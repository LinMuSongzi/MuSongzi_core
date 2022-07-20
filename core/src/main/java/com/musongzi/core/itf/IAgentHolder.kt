package com.musongzi.core.itf

import com.musongzi.core.itf.holder.IHolderLifecycle

interface IAgentHolder<V:IViewInstance> : IBusiness {

    fun setAgentModel(v: V)

}