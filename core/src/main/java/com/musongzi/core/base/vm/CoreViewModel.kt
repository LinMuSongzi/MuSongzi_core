package com.musongzi.core.base.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.musongzi.core.HolderLifeApi
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.IRxObserverEasyHelp
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.trello.rxlifecycle4.LifecycleTransformer

abstract class CoreViewModel<H : IHolderActivity> : ViewModel(), IAttach<H>, IRxObserverEasyHelp {

    protected var holderActivity: IHolderActivity? = null
    protected lateinit var mHolderLifeApi: IHolderApi

    override fun attachNow(t: H?) {
        holderActivity = t;
        mHolderLifeApi = createLifeApi();
    }

    fun createLifeApi(): IHolderApi {
        return HolderLifeApi(this)
    }

    override fun clear() {
        holderActivity = null;
    }


    fun disconnect() {
        holderActivity?.getClient()?.disconnect()
    }

    override fun isAttachNow(): Boolean = holderActivity != null

    override fun <T> bindToLifecycle(): LifecycleTransformer<T>? {
        return holderActivity?.bindToLifecycle()
    }

}