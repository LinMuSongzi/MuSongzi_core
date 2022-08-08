package com.musongzi.core.base.bean

import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.data.IChoose

open class BaseChooseBean : IChoose ,IAttribute{

    var chooseFlag = false

    var id_ = ""

    override fun isChoose(): Boolean = chooseFlag

    override fun choose(b: Boolean) {
        chooseFlag = b
    }

    override fun getAttributeId(): String {
        return id_;
    }
}