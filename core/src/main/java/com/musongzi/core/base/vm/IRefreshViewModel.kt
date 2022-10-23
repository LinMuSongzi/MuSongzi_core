package com.musongzi.core.base.vm

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.holder.IHolderClient
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderNeed
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.ISource

interface IRefreshViewModel<Item> : IHolderContext ,ILifeSaveStateHandle, IHolderNeed {
    fun getBundle(): Bundle?
    fun getHolderViewModelProvider():IHolderViewModelProvider?
    fun refreshHolderClient(): IRefreshClient<Item>?
    fun getHolderSource():ISource<Item>?{
        return null
    }
}