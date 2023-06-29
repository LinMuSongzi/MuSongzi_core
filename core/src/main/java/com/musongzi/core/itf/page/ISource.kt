package com.musongzi.core.itf.page

interface ISource<I> {

    fun realData(): List<I>

    @JvmDefault
    operator fun get(position:Int):I{
       return realData()[position]
    }


}