package com.musongzi.core.itf

interface IAgentWrap<V: IViewInstance> : IBusiness {

    fun setAgentModel(v: V)

}