package com.musongzi.music.bean

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/8/3 */
open class BaseAttribute: IAttribute {

    private lateinit var attributeId:String

    constructor():super()

    constructor(id:String):super(){
        this.attributeId = id
    }

    override fun getAttributeId(): String = attributeId
}