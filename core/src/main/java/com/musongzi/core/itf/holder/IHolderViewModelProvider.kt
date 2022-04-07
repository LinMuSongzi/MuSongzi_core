package com.musongzi.core.itf.holder

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

interface IHolderViewModelProvider :ViewModelStoreOwner{

    fun getMainViewModelProvider():ViewModelStoreOwner

}