package com.musongzi.core.itf.holder

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

interface IHolderViewModelProvider {

    fun isRootProvider() = topViewModelProvider() == thisViewModelProvider();

    fun topViewModelProvider(): ViewModelProvider?

    fun thisViewModelProvider(): ViewModelProvider?

}