package com.musongzi.core.base.client

import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.business.collection.IHolderCollections

interface CollectionsViewClient : IRefreshClient<Any> {
    fun getCollectionsViewEngine(): IHolderCollections?
    fun updateTitle(aNull: String)
    fun getViewModelProvider(thisOrTopProvider: Boolean): ViewModelProvider?
    fun engineName():String?
}