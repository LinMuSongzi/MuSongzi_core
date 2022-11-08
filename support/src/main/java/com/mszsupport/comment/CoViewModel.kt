package com.mszsupport.comment

import androidx.lifecycle.ViewModel
import com.mszsupport.itf.IActivityView
import com.mszsupport.itf.IBusiness
import com.mszsupport.itf.holder.IHolderViewModel
import com.mszsupport.util.InjectionHelp


open class CoViewModel<B : IBusiness> : ViewModel(), IHolderViewModel<B> by ViewModelSupportImpl() {

    val TAG = javaClass.canonicalName

    private lateinit var business: B

    override fun attachNow(t: IActivityView?) {
        val c = InjectionHelp.findGenericClass<B>(javaClass, actualTypeArgumentsViewModelIndex())
        val business = c.newInstance()
        (business as? BaseWrapBusiness<*>)?.apply {
            setAgentModel(this as Nothing)
            business.afterHandlerBusiness()
        }
        this.business = business
    }

    fun actualTypeArgumentsViewModelIndex():Int{
        return 0;
    }

    override fun getHolderBusiness(): B {

        return business
    }

}