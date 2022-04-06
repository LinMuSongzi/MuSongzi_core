package com.musongzi.core.itf.holder

import androidx.databinding.ViewDataBinding

interface IHolderDataBinding<D : ViewDataBinding> {

    fun getHolderDataBinding():D?

}