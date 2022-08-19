package com.musongzi.comment.business

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.base.business.itf.IHolderSupportActivityBusiness
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.itf.INeed
import com.musongzi.core.itf.IViewInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import java.lang.Exception

/*** created by linhui * on 2022/8/16
 *
 *
 * 新增一个权限简单访问
 *
 * */
class PermissionHelpBusiness @JvmOverloads constructor(actvity: ComponentActivity? = null) :
    BaseMapBusiness<IViewInstance>() {

    private lateinit var actvity: ComponentActivity

    init {
        if (actvity != null) {
            this.actvity = actvity
        }
    }


    //    private val LOCK = Object()
    private val map = HashMap<String, Boolean>()
    private var flagState = 0
    private lateinit var proxy: PermissionObservable

    private lateinit var mActivityResultLauncher: ActivityResultLauncher<Array<String>>

    fun observablePermission(permissions: Array<String>): PermissionObservable? {
        return (ActivityLifeManager.getInstance()
            .getTopActivity() as? ComponentActivity)?.let { actvity ->

            if (actvity.isFinishing || flagState.and(START) > 0) {
                return null
            }
            PermissionHelpBusiness@ this.actvity = actvity
            if (flagState.and(REGISTER_LIFY) == 0) {
                proxy = PermissionObservable(Observable.create<Map<String, Boolean>> {
                    if (flagState.and(DESTORY) == 0) {
                        it.onNext(map)
                        it.onComplete()
                    } else {
                        it.onError(Exception("activity is finish"))
                    }
                }.observeOn(AndroidSchedulers.mainThread()))
                mActivityResultLauncher =
                    actvity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { m ->
                        map.clear()
                        map.putAll(m)
                        proxy.ob.sub {
                            val gRunMap = HashMap<String, Boolean>()
                            val nRunMap = HashMap<String, Boolean>()
                            for (k in it.keys) {
                                val v = it[k];
                                if (v != null) {
                                    if (v) gRunMap[k] = v else nRunMap[k] = v
                                }
                            }
                            if (gRunMap.size == it.size && gRunMap.size > 0) {
                                proxy.onGrantedAllRun?.invoke()
                                proxy.onGrantedArrayRun?.invoke(gRunMap)
                            } else {
                                proxy.onGrantedArrayRun?.invoke(gRunMap)
                                proxy.onNotAuthorizedArrayRun?.invoke(nRunMap)
                            }
                            flagState = flagState.and(START.inv())
                        }
                    }
                actvity.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        flagState = flagState.or(DESTORY)
                    }
                })
                flagState = flagState.or(REGISTER_LIFY)
            }

            flagState = flagState.or(START)

            proxy.apply {
                this.permissions = permissions
            }
        }
    }

    companion object {
        const val START = 1
        const val REGISTER_LIFY = START.shl(1)
        const val DESTORY = START.shl(2)
        const val NORMAL = 0

        @JvmStatic
        fun PermissionObservable.onGranted(run: (Map<String, Boolean>) -> Unit): PermissionObservable {
            this.onGrantedArrayRun = run
            return this
        }

        @JvmStatic
        fun PermissionObservable.onNotAuthorizedArray(run: (Map<String, Boolean>) -> Unit): PermissionObservable {
            this.onNotAuthorizedArrayRun = run
            return this
        }

        @JvmStatic
        fun PermissionObservable.onGrantedAll(run: () -> Unit): PermissionObservable {
            this.onGrantedAllRun = run
            return this
        }

        @JvmStatic
        fun INeed.quickRequestPermission(array: Array<String>, onGrantedAll: () -> Unit) {
            getNext(PermissionHelpBusiness::class.java)!!.observablePermission(array)
                ?.onGrantedAll(onGrantedAll)?.subscribe()
        }

        @JvmStatic
        fun INeed.quickRequestCamera(onGrantedAll: () -> Unit) {
            quickRequestPermission(arrayOf(android.Manifest.permission.CAMERA),onGrantedAll)
        }

        @JvmStatic
        fun INeed.quickRequestCamera(
            notAuthorized: (Map<String, Boolean>) -> Unit,
            granted: (Map<String, Boolean>) -> Unit
        ) {
            quickRequestPermission(
                arrayOf(android.Manifest.permission.CAMERA),
                notAuthorized,
                granted
            )
        }


        @JvmStatic
        fun INeed.quickRequestPermission(
            array: Array<String>,
            notAuthorized: (Map<String, Boolean>) -> Unit,
            granted: (Map<String, Boolean>) -> Unit
        ) {
            getNext(PermissionHelpBusiness::class.java)!!.observablePermission(array)
                ?.onGranted(granted)?.onNotAuthorizedArray(notAuthorized)?.subscribe()
        }

        fun ComponentActivity.getNext(): INeed? {
            return if (this is IHolderSupportActivityBusiness) {
                getHolderSupprotActivityBusiness()
            } else
                null;
        }

    }


    inner class PermissionObservable(
        internal var ob: Observable<Map<String, Boolean>>
    ) {

        lateinit var permissions: Array<String>

        internal var onGrantedArrayRun: ((Map<String, Boolean>) -> Unit)? = null
        internal var onNotAuthorizedArrayRun: ((Map<String, Boolean>) -> Unit)? = null
        internal var onGrantedAllRun: (() -> Unit)? = null

        fun subscribe() {
            mActivityResultLauncher.launch(permissions)
        }
    }

}