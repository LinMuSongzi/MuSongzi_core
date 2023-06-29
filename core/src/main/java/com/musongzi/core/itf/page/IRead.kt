package com.musongzi.core.itf.page

interface IRead {

    fun refresh()
    fun next()

    val businessMode:Int
        get() = SIMPLE_MODE


    companion object{
        const val SIMPLE_MODE = 0x2
        const val POSTION_MODE = 0x4
    }


}