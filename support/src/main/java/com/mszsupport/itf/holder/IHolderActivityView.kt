package com.mszsupport.itf.holder

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mszsupport.itf.IActivityView

interface IHolderActivityView : LifecycleOwner {

    fun getHolderActivityView(): IActivityView?




}

fun IHolderActivityView.getSafeAtivityView(): IActivityView? =
    if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        getHolderActivityView()
    } else {
        null
    }