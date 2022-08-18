package com.musongzi.core.base.client

import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderViewModelProvider

interface CollectionsViewClient:IClient,IHolderViewModelProvider {
    fun getCollectionsViewEngine(): IHolderCollections?
    fun updateTitle(aNull: String)
//    fun getViewModelProvider(thisOrTopProvider: Boolean): ViewModelProvider?
    fun engineName():String?
    fun <I> getRefreshClient():IRefreshClient<I>
}