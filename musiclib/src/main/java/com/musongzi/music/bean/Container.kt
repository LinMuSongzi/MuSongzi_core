package com.musongzi.music.bean

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28  */
open class Container<D> @JvmOverloads constructor(name:String? = null) : BaseAttribute(name) {

    var child: D? = null

    fun setAttributeId(name:String){
        super.attribute_Id = name
    }

}