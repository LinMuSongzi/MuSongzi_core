package com.musongzi.core.itf

import androidx.lifecycle.LifecycleOwner

interface ILifeObject{

    fun getThisLifecycle(): LifecycleOwner?

}