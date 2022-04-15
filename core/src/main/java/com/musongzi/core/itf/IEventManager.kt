package com.musongzi.core.itf

import java.lang.reflect.InvocationHandler

interface IEventManager :ISingleManager {
    fun <T> put(name: Class<T>, h: () -> T)
    fun <T> remove(name: Class<T>, callBack: T)
}