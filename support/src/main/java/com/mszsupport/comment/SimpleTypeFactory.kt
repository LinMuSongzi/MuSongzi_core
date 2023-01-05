package com.mszsupport.comment

import com.mszsupport.itf.IBusiness
import com.mszsupport.itf.ITypeFactory
import com.mszsupport.itf.holder.IHolderViewModel
import com.mszsupport.util.InjectionHelp

class SimpleTypeFactory <V:IHolderViewModel<*>>(val viewModel: V, val index:()->Int) :ITypeFactory {

    override fun  <B> createInstance(): B? {
       val c = InjectionHelp.findGenericClass<B>(viewModel.javaClass,index())
        val business = c.newInstance()
        (business as? BaseWrapBusiness<V>)?.apply {
            setAgentModel(viewModel)
            business.afterHandlerBusiness()
        }
        return business
    }

//    override fun <B> createInstance(): B? {
//        TODO("Not yet implemented")
//    }
}