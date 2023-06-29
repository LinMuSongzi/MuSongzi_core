package com.musongzi.core.itf.page

import io.reactivex.rxjava3.core.Observable

interface IDataObservable2<D> {

    fun getRemoteData(page: Int, pageSize: Int): Observable<D>?

}