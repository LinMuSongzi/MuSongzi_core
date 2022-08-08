package com.musongzi.music.bean

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28  */
open class Container<D> : BaseAttribute() {

    var child: D? = null

}