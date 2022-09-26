package com.musongzi.core.base.vm

import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.holder.IHolderBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.page.ISource

/**
 * 一个允许挑选的，viewmodel
 */
interface IHandlerChooseViewModel<B : IBusiness> : IHolderLifecycle, IHolderBusiness<B> {

    fun updateByPick(pickData: IChoose?);

}