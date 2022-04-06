package com.musongzi.test.simple

import com.musongzi.core.base.vm.MszViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.test.Api

class BaseViewModel<C : IClient, B : IBusiness>: MszViewModel<C, B>() {


    override fun <T> getRetrofitApiInstance() :T{
        return RetrofitManager.getApi() as T
    }


}