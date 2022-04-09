package com.musongzi.core.base.bean

import com.musongzi.core.itf.data.IChoose

open class BaseChooseBean : IChoose {

    var chooseFlag = false


    override fun isChoose(): Boolean = chooseFlag

    override fun choose(b: Boolean) {
        chooseFlag = b
    }
}