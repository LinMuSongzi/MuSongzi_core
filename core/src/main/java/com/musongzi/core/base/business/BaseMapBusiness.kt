package com.musongzi.core.base.business

import android.util.Log
import com.musongzi.core.base.business.itf.ILightWeightBus
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.itf.*
import com.musongzi.core.util.InjectionHelp
import java.lang.Exception
import kotlin.jvm.Throws

/*** created by linhui * on 2022/7/6 */
abstract class BaseMapBusiness<V : IViewInstance> : BaseWrapBusiness<V>(), IHolderSavedStateHandle {
    @JvmField
    protected val TAG = javaClass.simpleName

    private var cacheBusinessMaps = HashMap<String, IBusiness>()
    private var hostSavedHandler: ISaveStateHandle = LocalSavedHandler()

    override fun getHolderSavedStateHandle(): ISaveStateHandle {
        return hostSavedHandler
    }

    //    @Deprecated("过期")
    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        this.hostSavedHandler = savedStateHandle;
    }

    override fun <Next : IBusiness> getNext(search: Class<Next>): Next? {
        return getNext(search.name, search)
    }

    @Throws(Exception::class)
    private fun <Next : IBusiness> getNext(
        searchString: String,
        searchClass: Class<Next>
    ): Next? {

        var business: Next? = cacheBusinessMaps[searchString] as? Next
        if (business == null) {
            if (!searchClass.isInterface) {
                val wrap: IBusiness? =
                    if (ILightWeightBus::class.java.isAssignableFrom(searchClass)) {
                        this
                    } else {
                        null
                    }
                InjectionHelp.injectBusiness(searchClass, iAgent, wrap)?.apply {
                    business = this
                    Log.i(TAG, "getNext instance: $business")
                    cacheBusinessMaps[searchString] = business!!
                }
            }
        } else {
            Log.i(TAG, "getNext: $business")
        }

        return business
    }
}