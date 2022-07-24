package com.musongzi.core.itf

interface IAgentHolder<V:IViewInstance> : IBusiness {

    fun setAgentModel(v: V)

}