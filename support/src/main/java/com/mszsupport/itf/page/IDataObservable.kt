package com.mszsupport.itf.page

import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/28  */
interface IDataObservable<D : Any> {
    fun getRemoteData(page: Int): Observable<D>?
}