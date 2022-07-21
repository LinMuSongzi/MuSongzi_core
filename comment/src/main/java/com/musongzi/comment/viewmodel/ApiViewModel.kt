package com.musongzi.comment.viewmodel

import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.vm.EasyViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.util.InjectionHelp

open class ApiViewModel<C : IClient, B : IBusiness, Api> : EasyViewModel<C, B>(), IHolderApi<Api> {

//    private val apiInstance: Api by lazy {
//
//    }

    override fun getApi(): Api {
        return RetrofitManager.getInstance().getApi(InjectionHelp.findGenericClass(this@ApiViewModel.javaClass, 2), this)
    }

}