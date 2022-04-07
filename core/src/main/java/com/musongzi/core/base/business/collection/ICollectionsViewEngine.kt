package com.musongzi.core.base.business.collection

import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.itf.page.IPageEngine


interface ICollectionsViewEngine<Item> : IPageEngine<Item>, IHolderCollections {

    fun getRefreshViewModel(): IRefreshViewModel<Item>?


}