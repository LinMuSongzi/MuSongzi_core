package com.musongzi.core.itf.page

import io.reactivex.rxjava3.core.Observable

/**
 * 数据加载接口
 * @param Entity
 */
interface IDataEngine<DATAS> : Book {
    fun getRemoteData(page: Int): Observable<DATAS>?
}