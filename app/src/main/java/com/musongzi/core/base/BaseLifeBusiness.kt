package com.musongzi.core.base

import androidx.lifecycle.DefaultLifecycleObserver
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHodlerLifecycle
import com.musongzi.core.itf.holder.IHodlerViewModel

open class BaseLifeBusiness<L : IAgent> : IBusiness, DefaultLifecycleObserver {


    protected lateinit var iAgent: L


    override fun afterHandlerBusiness() {

    }

    fun setAgentModel(l: L) {
        this.iAgent = l;
    }

}
