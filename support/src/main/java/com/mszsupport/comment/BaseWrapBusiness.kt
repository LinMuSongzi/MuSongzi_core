package com.mszsupport.comment

import com.mszsupport.itf.IBusiness
import com.mszsupport.itf.IViewInstance
import com.mszsupport.itf.IWarpBusiness


/*** created by linhui * on 2022/9/23 */
open class BaseWrapBusiness<V : IViewInstance> @JvmOverloads constructor(dependBusiness: IBusiness? = null) :
    IWarpBusiness<V> {

    protected lateinit var iAgent: V
    private var dependBusiness: IBusiness? = null

    init {
        if(dependBusiness != null){
            this.dependBusiness = dependBusiness
        }
    }

    override fun afterHandlerBusiness() {
        dependBusiness?.afterHandlerBusiness()
    }

    override fun <Next : IBusiness> getNext(sreach: Class<Next>): Next? {
       return dependBusiness?.getNext(sreach)
    }

    override fun setAgentModel(v: V) {
        this.iAgent = v
    }


}