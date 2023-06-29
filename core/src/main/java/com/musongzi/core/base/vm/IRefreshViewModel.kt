package com.musongzi.core.base.vm

import android.os.Bundle
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.itf.ILifeSaveStateHandler
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderNeed
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.ISource

interface IRefreshViewModel<Item> : IHolderContext ,ILifeSaveStateHandler, IHolderNeed {
    fun getBundle(): Bundle?
    fun getHolderViewModelProvider():IHolderViewModelProvider?
    fun getRefreshClient(): IRefreshClient<Item>?
    fun getHolderSource():ISource<Item>?{
        return null
    }
}