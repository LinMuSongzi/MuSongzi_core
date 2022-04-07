package com.musongzi.core.itf

interface IAttach<T> {

    fun attachNow(t: T?)

    fun clear();

    fun isAttachNow(): Boolean

}