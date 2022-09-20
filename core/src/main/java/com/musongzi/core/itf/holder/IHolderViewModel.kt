package com.musongzi.core.itf.holder

import android.os.Bundle
import com.musongzi.core.itf.*

interface IHolderViewModel<B : IBusiness> : IAgent, IAttach<IHolderActivity>, IHolderBusiness<B>, IHolderArguments<Bundle>, ILifeSaveStateHandle {

    @Deprecated("~暂时先标记过时")
    fun handlerSavedInstanceState(savedInstanceState: Bundle?);

    @Deprecated("~暂时先标记过时")
    fun isSavedInstanceStateNull(): Boolean

//    fun localLifeSaveStateHandle():I


}