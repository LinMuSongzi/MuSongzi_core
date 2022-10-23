package com.mszsupport.itf.holder

import com.mszsupport.itf.*

interface IHolderViewModel<B : IBusiness> : IAgent, IAttach<IActivityView>, IHolderBusiness<B>, ILifeSaveStateHandle, IHolderSavedStateHandle2 {

//    @Deprecated("~暂时先标记过时")
//    fun handlerSavedInstanceState(savedInstanceState: Bundle?);
//
//    @Deprecated("~暂时先标记过时")
//    fun isSavedInstanceStateNull(): Boolean

//    fun localLifeSaveStateHandle():I


}