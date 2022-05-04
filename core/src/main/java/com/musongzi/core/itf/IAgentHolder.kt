package com.musongzi.core.itf

import com.musongzi.core.itf.holder.IHolderLifecycle

interface IAgentHolder<V : IHolderLifecycle> : IBusiness {

    fun setAgentModel(v: V)

}