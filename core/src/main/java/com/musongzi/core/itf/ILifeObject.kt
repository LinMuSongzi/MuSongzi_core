package com.musongzi.core.itf

import androidx.lifecycle.LifecycleOwner

interface ILifeObject :IViewInstance {

    fun getThisLifecycle(): LifecycleOwner?

}