package com.musongzi.core.itf.holder

import androidx.databinding.ViewDataBinding
import com.musongzi.core.itf.IClient

interface IHolderDataBinding<D : ViewDataBinding> {

    fun getHolderDataBinding():D?



}