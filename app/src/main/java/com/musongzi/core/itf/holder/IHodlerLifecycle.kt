package com.musongzi.core.itf.holder

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.ILifeObject

interface IHodlerLifecycle : ILifeObject {

    fun getMainLifecycle(): IHodlerLifecycle?

}