package com.musongzi.core.base.business

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness
import org.greenrobot.eventbus.EventBus

open class BaseLifeBusiness<L : IAgent> : IBusiness, DefaultLifecycleObserver {

    @JvmField
    protected val TAG = javaClass.simpleName

    protected lateinit var iAgent: L


    override fun afterHandlerBusiness() {

    }

    fun setAgentModel(l: L) {
        this.iAgent = l;
        l.getThisLifecycle()?.lifecycle?.addObserver(this)
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

    protected  open fun isEnableEventBus() = false

}
