package com.mszsupport.itf.holder

import com.mszsupport.itf.ILifeObject

interface IHolderLifecycle : ILifeObject {

    fun getMainLifecycle(): IHolderLifecycle?

}