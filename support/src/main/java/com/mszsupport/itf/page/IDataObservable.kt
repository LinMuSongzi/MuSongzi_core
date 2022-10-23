package com.musongzi.core.itf.page

import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/28  */
interface IDataObservable<D> {
    fun getRemoteData(page: Int): Observable<D>?
}