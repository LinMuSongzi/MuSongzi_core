package com.musongzi.core.itf

interface IEventManager : ISingleManager {
    fun <T> put(name: Class<T>, h: () -> T)
    fun <T> remove(name: Class<T>, callBack: T)
    fun <T> asInterface(clazz: Class<T>): T?
    fun holderMap(name: Class<*>): Set<Any>
}