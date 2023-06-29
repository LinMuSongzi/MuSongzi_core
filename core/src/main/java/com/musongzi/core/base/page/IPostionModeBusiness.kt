package com.musongzi.core.base.page

interface IPostionModeBusiness<I> {

    fun <I> convertListByNewData(data: MutableList<I>, transList: MutableList<I>?)

}