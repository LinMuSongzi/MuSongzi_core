package com.mszsupport.itf

import androidx.lifecycle.LifecycleOwner

interface ILifeObject :IViewInstance {

    fun getThisLifecycle(): LifecycleOwner?

}