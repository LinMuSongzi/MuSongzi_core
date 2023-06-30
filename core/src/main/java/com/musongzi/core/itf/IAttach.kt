package com.musongzi.core.itf

interface IAttach<T> :IClear{

    fun attachNow(t: T?)

    fun isAttachNow(): Boolean

}