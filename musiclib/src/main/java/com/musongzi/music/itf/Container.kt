package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28  */
internal class Container<D>(private var cId:String):IAttribute {

    var detail: D? = null

    override fun getAttributeId(): String = cId
}