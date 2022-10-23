package com.mszsupport.itf

interface IAgentHolder<V: IViewInstance> : IBusiness {

    fun setAgentModel(v: V)

}