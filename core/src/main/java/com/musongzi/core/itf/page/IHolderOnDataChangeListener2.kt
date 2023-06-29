package com.musongzi.core.itf.page

import com.musongzi.core.base.page2.OnPageDataChange2

interface IHolderOnDataChangeListener2<I,A> {

    fun addOnPageDataChange(onDataChange: OnPageDataChange2<I, A>): Boolean

    fun removeOnPageDataChange(onDataChange: OnPageDataChange2<I, A>?): Boolean

}