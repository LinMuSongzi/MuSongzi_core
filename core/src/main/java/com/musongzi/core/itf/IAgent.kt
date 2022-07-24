package com.musongzi.core.itf

import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.itf.holder.IHolderLifecycle

interface IAgent: IHolderLifecycle,IClient {

    fun getHolderClient():IClient?

    fun <Api> holderApiInstance():IHolderApi<Api>?

}