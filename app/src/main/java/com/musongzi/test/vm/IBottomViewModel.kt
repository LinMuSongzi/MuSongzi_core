package com.musongzi.test.vm

import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.IViewInstance

interface IBottomViewModel:ILifeSaveStateHandle {


    fun remoteData()

}