package com.musongzi.music.bean

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/8/3 */
open class BaseAttribute @JvmOverloads constructor(id:String? = null): IAttribute {
    protected lateinit var attribute_Id:String
    init {
        if(id != null){
            attribute_Id = id
        }
    }



    override fun getAttributeId(): String = attribute_Id
}