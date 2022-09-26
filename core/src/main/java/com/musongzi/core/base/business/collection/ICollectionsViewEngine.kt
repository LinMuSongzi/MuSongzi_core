package com.musongzi.core.base.business.collection

import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.itf.INeed
import com.musongzi.core.itf.holder.IHolderNeed
import com.musongzi.core.itf.page.IPageEngine


interface ICollectionsViewEngine<Item> : IPageEngine<Item>, IHolderCollections, IHolderNeed{

    fun getRefreshViewModel(): IRefreshViewModel<Item>?

    override fun getHolderNeed(): INeed? {
       return getRefreshViewModel()?.getHolderNeed()
    }

}