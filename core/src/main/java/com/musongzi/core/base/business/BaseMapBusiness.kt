package com.musongzi.core.base.business

import android.util.Log
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.itf.*
import com.musongzi.core.util.InjectionHelp
import java.lang.Exception
import kotlin.jvm.Throws

/*** created by linhui * on 2022/7/6 */
abstract class BaseMapBusiness<L: IViewInstance> : IAgentHolder<L> ,IHolderSavedStateHandle{
    @JvmField
    protected val TAG = javaClass.simpleName

    private var cacheBusinessMaps = HashMap<String, IBusiness>()
    private var hostSavedHandler:ISaveStateHandle = LocalSavedHandler()

    override fun getHolderSavedStateHandle(): ISaveStateHandle {
       return hostSavedHandler
    }

//    @Deprecated("过期")
    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
       this.hostSavedHandler = savedStateHandle;
    }

    protected lateinit var iAgent: L


    override fun afterHandlerBusiness() {
    }

    override fun setAgentModel(v: L) {
        Log.i(TAG, "setAgentModel: now set")
        this.iAgent = v;
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
            if(!searchClass.isInterface) {
                InjectionHelp.injectBusiness(searchClass,iAgent)?.apply {
                    business = this
                    Log.i(TAG, "getNext instance: $business")
                    cacheBusinessMaps[searchString] = business!!
                }
            }
        }else{
            Log.i(TAG, "getNext: $business")
        }

        return business
    }
}