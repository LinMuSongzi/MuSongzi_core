package com.mszsupport.itf

import com.mszsupport.itf.holder.IHolderLifecycle
import io.reactivex.rxjava3.core.ObservableTransformer

interface IWant : IHolderLifecycle {


    fun <T : Any> bindToLifecycle(): ObservableTransformer<T, T>?

}