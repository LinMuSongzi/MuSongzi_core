package com.musongzi.core.base.map

import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ISaveStateHandle

/*** created by linhui * on 2022/7/27 */
class HolderSavedStateHandleImpl(var savedHandler: ISaveStateHandle) :IHolderSavedStateHandle{
    override fun getHolderSavedStateHandle() = savedHandler

    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        savedHandler = savedStateHandle
    }


}