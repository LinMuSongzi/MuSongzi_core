package com.musongzi.comment.viewmodel

import com.musongzi.core.base.vm.MszViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.util.InjectionHelp

open class ApiViewModel<C : IClient, B : IBusiness, Api> : MszViewModel<C, B>(), IHolderApi<Api> {

    override fun getApi(): Api {
        return InjectionHelp.injectApi(this,indexApiActualTypeArgument())
    }

    protected open fun indexApiActualTypeArgument() = API_TYPE_INDEX


    companion object{
        const val API_TYPE_INDEX = 2
    }

}