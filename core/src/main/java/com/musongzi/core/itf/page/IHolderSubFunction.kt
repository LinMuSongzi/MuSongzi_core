package com.musongzi.core.itf.page

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

interface IHolderSubFunction<A : Observable<D>, D> {

    fun getSubFunction(): ((A, Observer<D>) -> Unit)?

}