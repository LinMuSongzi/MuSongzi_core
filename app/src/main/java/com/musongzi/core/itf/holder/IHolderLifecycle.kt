package com.musongzi.core.itf.holder

import com.musongzi.core.itf.ILifeObject

interface IHolderLifecycle : ILifeObject {

    fun getMainLifecycle(): IHolderLifecycle?

}