package com.musongzi.comment.ad

import io.reactivex.rxjava3.core.Observer

/*** created by linhui * on 2022/7/18 */
interface IAdLoader {


    fun <T> loadRemoteConfig(observer: Observer<T>)

    fun getAdController():IAdController

}