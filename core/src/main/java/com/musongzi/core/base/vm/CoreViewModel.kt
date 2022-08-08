package com.musongzi.core.base.vm

import androidx.lifecycle.ViewModel
import com.musongzi.core.base.map.HolderSavedStateHandleImpl
import com.musongzi.core.base.map.HostSavedHandler
import com.musongzi.core.base.map.SaveStateHandleWarp
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.IHolderActivity
import com.trello.rxlifecycle4.LifecycleTransformer

abstract class CoreViewModel<H : IHolderActivity> : ViewModel(), IAttach<H>, IWant, IDisconnect {

    companion object {
        const val LOCAL_SAVED_INDEX = 1;
        const val REMOTE_SAVED_INDEX = 0;
        const val SAVEDS_MAX = LOCAL_SAVED_INDEX + REMOTE_SAVED_INDEX + 1
    }

    protected var holderActivity: IHolderActivity? = null

    protected var mSavedStateHandles = Array<ISaveStateHandle?>(SAVEDS_MAX) {
        null
    }

    fun localSavedStateHandle(): ISaveStateHandle {
        return mSavedStateHandles[LOCAL_SAVED_INDEX] ?: HostSavedHandler().apply {
            mSavedStateHandles[LOCAL_SAVED_INDEX] = this
        }
    }

    override fun attachNow(t: H?) {
        holderActivity = t;
    }

    override fun clear() {
        holderActivity = null;
    }

    override fun disconnect(): Boolean {
        return holderActivity?.getClient()?.disconnect() ?: true
    }

    override fun isAttachNow(): Boolean = holderActivity != null

    override fun <T> bindToLifecycle(): LifecycleTransformer<T>? {
        return holderActivity?.bindToLifecycle()
    }

//    companion object{
//        const val
//    }

}