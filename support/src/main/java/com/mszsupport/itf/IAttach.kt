package com.mszsupport.itf

interface IAttach<T> {

    fun attachNow(t: T?)

    fun clear();

    fun isAttachNow(): Boolean

}