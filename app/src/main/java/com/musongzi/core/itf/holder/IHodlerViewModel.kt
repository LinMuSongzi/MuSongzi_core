package com.musongzi.core.itf.holder

import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient

interface IHodlerViewModel<C : IClient,B : IBusiness> : IAgent, IAttach<IHodlerActivity>, IHolderBusiness<B>,IHodlerClient<C>{

//    fun getHolderClient(): IClient?


}