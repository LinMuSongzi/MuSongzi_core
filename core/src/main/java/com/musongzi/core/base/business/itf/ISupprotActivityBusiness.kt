package com.musongzi.core.base.business.itf

import androidx.databinding.ViewDataBinding
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderDataBinding
import com.musongzi.core.itf.holder.IHolderViewModelProvider

/*** created by linhui * on 2022/7/6
 *
 * 提供给activity的一个封装类
 *
 * 里面包含基于livedata的了 [IHolderSavedStateHandle] 和本地数据访问[getLocalHolderSavedStateHandle]
 * 提供了基本的视图[getHolderDataBinding]
 *
 * */
interface ISupprotActivityBusiness :IBusiness,IHolderContext,IDisconnect,IHolderSavedStateHandle,IHolderDataBinding<ViewDataBinding>
    ,IHolderViewModelProvider{
    fun checkEvent()
    fun getLocalHolderSavedStateHandle():ISaveStateHandle
    override fun getHolderDataBinding():ViewDataBinding
}