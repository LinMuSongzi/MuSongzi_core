package com.mszsupport.itf

interface IHolderSavedStateHandle {

   fun getHolderSavedStateHandle(): ISaveStateHandle

   fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle)

}