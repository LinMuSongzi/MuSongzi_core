package com.musongzi.core.base.page2

import io.reactivex.rxjava3.core.Observable


class RequestObservableBean<D> {
    var baseData: D? = null
    var pageSize: Int = 0
    var page: Int = 0
    lateinit var requestObservable: Observable<D>
}