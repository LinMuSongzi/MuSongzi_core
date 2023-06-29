package com.musongzi.core.itf.page

interface IHolderOnDataChangeListener<T> {

//    val onPageDataChangess:MutableSet<OnPageDataChange<T>>
    fun addOnPageDataChange(onDataChange: OnPageDataChange<T>): Boolean

    fun removeOnPageDataChange(onDataChange: OnPageDataChange<T>?): Boolean

}