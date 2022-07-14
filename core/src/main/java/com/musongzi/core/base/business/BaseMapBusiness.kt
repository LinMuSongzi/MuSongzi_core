package com.musongzi.core.base.business

import android.util.Log
import com.musongzi.core.itf.IAgentHolder
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import java.lang.Exception
import kotlin.jvm.Throws

/*** created by linhui * on 2022/7/6 */
abstract class BaseMapBusiness<L: IHolderLifecycle> : IAgentHolder<L> {
    @JvmField
    protected val TAG = javaClass.simpleName

    private var cacheBusinessMaps = HashMap<String, IBusiness>()

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
                business = searchClass.newInstance()
                (business as? IAgentHolder<IHolderLifecycle>)?.let {
                    try {
                        it.setAgentModel(iAgent)
                        it.afterHandlerBusiness();
                        cacheBusinessMaps[searchString] = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                        business = null
                    }
                }
            }
        }
        Log.i(TAG, "getNext: $business")
        return business
    }
}