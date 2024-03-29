package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.data.IHolderDataConvert
import com.musongzi.core.itf.page.IDataEngine
import com.musongzi.core.itf.page.IHolderPageEngine
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource

/*** created by linhui * on 2022/7/28
 *
 *
 *
 *
 * */
interface IAttributeArray<I:IAttribute> : IAttribute, ISource<I>,IHolderPageEngine<I> {

    fun contains(att:IAttribute,find: ((IAttribute) -> Boolean)? = null):Boolean


}