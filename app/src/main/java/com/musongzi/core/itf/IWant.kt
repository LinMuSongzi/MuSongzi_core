package com.musongzi.core.itf

import com.musongzi.core.itf.holder.IHolderLifecycle
import com.trello.rxlifecycle4.LifecycleTransformer
import io.reactivex.rxjava3.core.ObservableTransformer

interface IWant :IHolderLifecycle{


    fun <T> bindToLifecycle(): LifecycleTransformer<T>?

}