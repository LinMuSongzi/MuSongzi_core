package com.musongzi.core.itf.holder

import com.musongzi.core.itf.ILifeObject

interface IHodlerLifecycle : ILifeObject {


    fun getMainLifecycle(): ILifeObject

}