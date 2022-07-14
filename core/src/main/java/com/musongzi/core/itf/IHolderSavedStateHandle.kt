package com.musongzi.core.itf

import androidx.lifecycle.SavedStateHandle

interface IHolderSavedStateHandle {

   fun getHolderSavedStateHandle(): ISaveStateHandle

   fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle)

}