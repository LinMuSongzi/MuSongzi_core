package com.musongzi.core.base.vm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.base.bean.BusinessInfo
import com.musongzi.core.base.map.SaveStateHandleWarp
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.util.InjectionHelp.BUSINESS_NAME_KEY
import java.lang.ref.WeakReference
/*** created by linhui * on 2022/9/15
 *
 * */
abstract class DataDriveViewModel<B : IBusiness> : CoreViewModel, IHolderViewModel<B>,
    IHolderNeed {


//    fun <T> observerByKey(key:String,lifecycleOwner: LifecycleOwner,observer: Observer<T>){
//        key.liveSaveStateObserver(lifecycleOwner,this,observer)
//    }
//
//    fun <T> observerLocalByKey(key:String,lifecycleOwner: LifecycleOwner,observer: Observer<T>){
//        key.liveSaveStateObserver(lifecycleOwner,localSavedStateHandle,observer)
//    }

    constructor():super(){
        Log.i(TAG, "constructor by : ()")
    }
    constructor(saved: SavedStateHandle) : super() {
        Log.i(TAG, "constructor by : (saved: SavedStateHandle)")
        setHolderSavedStateHandle(SaveStateHandleWarp(saved))
    }

    private val businessInfo: BusinessInfo?
        get() {
            return getHolderSavedStateHandle()?.run {
                get(BUSINESS_NAME_KEY) as? BusinessInfo
            }
        }
    private val business: B by lazy {
        val b = createBusiness()
        (b as? IAgentWrap<IAgent>)?.setAgentModel(agentGet())
        b.afterHandlerBusiness()
        b
    }
    private var savedInstanceState: WeakReference<Bundle?>? = null


    override fun getHolderNeed(): INeed? {
        return getHolderBusiness()
    }


    override fun showDialog(msg: String?) {
        getHolderClient()?.showDialog(msg)
    }

    override fun disimissDialog() {
        getHolderClient()?.disimissDialog()
    }

    override fun getHolderClient(): IClient? {
        return holderActivity?.get()
    }

    final override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        Log.i(TAG, "setHolderSavedStateHandle: ${javaClass.canonicalName} , " + savedStateHandle)
        super.mSavedStateHandles[REMOTE_SAVED_INDEX] = savedStateHandle
    }

    final override fun getHolderSavedStateHandle(): ISaveStateHandle {
        return super.mSavedStateHandles[REMOTE_SAVED_INDEX]
            ?: throw ExceptionInInitializerError("DataDriveViewModel的子类注入方式错误 ${javaClass.canonicalName}")
    }

    protected open fun createBusiness2(): B {
        TODO("Not yet implemented")
    }

    override fun getHolderBusiness(): B = business

    protected open fun indexBusinessActualTypeArgument() = 0

    protected open fun agentGet(): IAgent {
        return this
    }

    private fun createBusiness(): B {
        return businessInfo?.let {
            Log.i(TAG, "createBusiness: 1")
            InjectionHelp.getClassLoader().loadClass(it.className)
                ?.newInstance() as? B
        } ?: (InjectionHelp.findGenericClass<B>(
            javaClass,
            indexBusinessActualTypeArgument()
        ).let {
            if (it.isInterface) {
                Log.i(TAG, "createBusiness: 2")
                createBusiness2()
            } else {
                Log.i(TAG, "createBusiness: 3")
                it.newInstance()
            }
        })
    }

}