package com.musongzi.core.itf

import com.musongzi.core.itf.holder.IHodlerClient
import com.musongzi.core.itf.holder.IHodlerLifecycle

interface IAgent: IHodlerLifecycle,IClient {

    fun getHolderClient():IClient?

}