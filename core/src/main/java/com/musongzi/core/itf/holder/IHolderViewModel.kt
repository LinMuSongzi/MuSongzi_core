package com.musongzi.core.itf.holder

import android.os.Bundle
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness

interface IHolderViewModel<B : IBusiness> : IAgent, IHolderBusiness<B>, IHolderAnySaveStateHandler {

    @Deprecated("~暂时先标记过时")
    fun handlerSavedInstanceState(savedInstanceState: Bundle?);

    @Deprecated("~暂时先标记过时")
    fun isSavedInstanceStateNull(): Boolean

//    fun localLifeSaveStateHandle():I


}