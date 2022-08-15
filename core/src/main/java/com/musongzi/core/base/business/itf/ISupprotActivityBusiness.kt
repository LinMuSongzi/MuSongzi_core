package com.musongzi.core.base.business.itf

import androidx.databinding.ViewDataBinding
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderDataBinding

/*** created by linhui * on 2022/7/6 */
interface ISupprotActivityBusiness :IBusiness,IHolderContext,IDisconnect,IHolderSavedStateHandle,IHolderDataBinding<ViewDataBinding>{
    fun checkEvent()
    fun getLocalHolderSavedStateHandle():ISaveStateHandle
    override fun getHolderDataBinding():ViewDataBinding
}