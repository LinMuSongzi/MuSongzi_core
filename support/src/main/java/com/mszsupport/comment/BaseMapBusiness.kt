package com.mszsupport.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mszsupport.itf.*
import com.mszsupport.itf.holder.IHolderViewModel
import java.lang.reflect.Constructor


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
                val wrap: IBusiness? = null
//                    if (ILightWeightBus::class.java.isAssignableFrom(searchClass)) {
//                        this
//                    } else {
//                        null
//                    }
                injectBusiness(searchClass, iAgent, wrap)?.apply {
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

fun <A : IViewInstance?, B : IBusiness?> injectBusiness(
    targetClass: Class<B>,
    agent: A
): B? {
    return injectBusiness(targetClass, agent, null)
}

fun <A : IViewInstance?, B : IBusiness?> injectBusiness(
    targetClass: Class<B>,
    agent: A,
    businessWrapInstance: IBusiness?
): B? {
    try {
        val constructor: Constructor<B> = if (businessWrapInstance == null) {
            targetClass.getConstructor()
        } else {
            targetClass.getConstructor(IBusiness::class.java)
        }
        return injectBusiness(constructor, agent, businessWrapInstance)
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    }
    return null
}

private fun <A : IViewInstance?, B : IBusiness?> injectBusiness(
    constructor: Constructor<B>,
    agent: A,
    businessWrapInstance: IBusiness?
): B? {
    var instance: B? = null
    try {
        instance = if (businessWrapInstance == null) {
            constructor.newInstance()
        } else {
            constructor.newInstance(businessWrapInstance)
        }


        if (instance is IAgentHolder<*>) {
            try {
                (instance as IAgentHolder<*>?)?.setAgentModel(agent as Nothing)
            } catch (ex: java.lang.Exception) {
//                Log.i(InjectionHelp.TAG, "injectBusiness: setAgent error in instance")
                ex.printStackTrace()
            }
        }
        instance?.afterHandlerBusiness()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return instance
}


//inline fun <reified M : ViewModelSupportImpl<B>, B : IBusiness> M.injectBusinessToModel():B {
//    M::class.get
//    getBusinessIndext()
//}
