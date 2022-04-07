package com.musongzi.core.base.business.collection

import android.view.View
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.itf.page.IPageEngine

interface IHolderCollections : CollectionsViewSupport {
    fun onRefreshViewClientEvent(i: IRefreshViewClient);
    fun init(i: IRefreshViewModel<*>);
    fun onEmptyViewCreate(v: View?)
    fun getPageSupport(): IPageEngine<*>
    fun getTag():String
}