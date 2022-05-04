package com.musongzi.core.base.business

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IAgentHolder
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

open class BaseLifeBusiness<L : IHolderLifecycle> : IAgentHolder<L>, DefaultLifecycleObserver{

    @JvmField
    protected val TAG = javaClass.simpleName
    private var cacheBusinessMaps = HashMap<String, IBusiness>()
    protected lateinit var iAgent: L


    override fun afterHandlerBusiness() {
        Log.i(TAG, "onCreateView:afterHandlerBusiness; -- ")
    }

    override fun <Next : IBusiness> getNext(search: Class<Next>): Next? {
        return getNext(search.name, search)
    }

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

    override fun setAgentModel(v: L) {
        Log.i(TAG, "setAgentModel: now set")
        this.iAgent = v;
        v.getThisLifecycle()?.lifecycle?.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        if (isEnableEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (isEnableEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    protected open fun isEnableEventBus() = false

}
