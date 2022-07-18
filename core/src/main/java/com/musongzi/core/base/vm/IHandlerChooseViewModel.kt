package com.musongzi.core.base.vm

import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.holder.IHolderBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.page.ISource

interface IHandlerChooseViewModel<B : IBusiness> : IHolderLifecycle, IHolderBusiness<B> {

    fun baseDatas(): ISource<IChoose>?

    fun updateByPick(pickData: IChoose?);

}