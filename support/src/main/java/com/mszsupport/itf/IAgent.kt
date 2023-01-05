package com.mszsupport.itf

import com.mszsupport.itf.holder.IHolderApi
import com.mszsupport.itf.holder.IHolderLifecycle

interface IAgent: IHolderLifecycle, IClient {

    fun getHolderClient(): IClient?

    fun <Api> holderApiInstance(): IHolderApi<Api>?

}