package com.musongzi.core.base.vm

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.itf.holder.IHolderContext

interface IRefreshViewModel<Item> : IRefreshClient<Item>, IHolderContext {
    fun getViewModelProvider(isTopViewProperty: Boolean): ViewModelProvider
    fun getBundle(): Bundle?
}