package com.musongzi.core.itf.holder

import android.os.Bundle
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient

interface IHolderViewModel<C : IClient, B : IBusiness> : IAgent, IAttach<IHolderActivity>, IHolderBusiness<B>, IHolderClient<C>, IHolderArguments<Bundle> {

    fun handlerSavedInstanceState(savedInstanceState: Bundle?);

    fun isSavedInstanceStateNull(): Boolean


}