package com.musongzi.core.itf.holder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.musongzi.core.ExtensionCoreMethod.liveSaveStateObserver

interface IHolderAnySaveStateHandler : IHolderSavedStateHandler, IHolderLocaSavedStateHandler {

//    fun <R>

    fun <T> observerByKey(key: String, lifecycleOwner: LifecycleOwner, observer: Observer<T?>) {
        key.liveSaveStateObserver(lifecycleOwner, this, observer)
    }

    fun <T> observerLocalByKey(key: String, lifecycleOwner: LifecycleOwner, observer: Observer<T?>) {
        key.liveSaveStateObserver(lifecycleOwner, localSavedStateHandle, observer)
    }

}