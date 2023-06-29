package com.musongzi.core.itf.holder

import com.musongzi.core.itf.ISaveStateHandle

/**
 * [ISaveStateHandle]持有者
 */
interface IHolderSavedStateHandler {

   fun getHolderSavedStateHandle(): ISaveStateHandle

   @Deprecated("过期")
   fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle)
}