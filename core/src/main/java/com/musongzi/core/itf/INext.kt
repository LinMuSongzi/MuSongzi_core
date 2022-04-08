package com.musongzi.core.itf

interface INext<T : INext<T>> {

    fun getNextByClass(nextClass: Class<*>): T?

}