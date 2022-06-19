package com.musongzi.core.itf

import androidx.lifecycle.SavedStateHandle

interface IHolderSavedStateHandle {

   fun getHolderSavedStateHandle(): SavedStateHandle?

   fun setHolderSavedStateHandle(savedStateHandle: SavedStateHandle)

}