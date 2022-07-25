package com.musongzi.core.base.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderActivity
import com.trello.rxlifecycle4.LifecycleTransformer

abstract class CoreViewModel<H : IHolderActivity> : ViewModel(), IAttach<H>, IWant, IDisconnect {

    protected var holderActivity: IHolderActivity? = null

    protected lateinit var mSavedStateHandle: ISaveStateHandle

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