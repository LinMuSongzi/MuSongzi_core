package com.musongzi.core.util

import android.os.Looper
import androidx.lifecycle.MutableLiveData

class MainLiveData<T> : MutableLiveData<T?> {


    constructor() : super()
    constructor(value: T? = null) : super(value)

    override fun setValue(value: T?) {
        if (Thread.currentThread() != Looper.getMainLooper().thread) {
            postValue(value)
            return
        }
        super.setValue(value)
    }

}