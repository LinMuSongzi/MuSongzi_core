package com.musongzi.core.base.vm

import androidx.lifecycle.ViewModel
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.holder.IHolderActivity

abstract class CoreViewModel<H : IHolderActivity> : ViewModel(), IAttach<H> {

    protected var holderActivity: IHolderActivity? = null

    override fun attachNow(t: H?) {
        holderActivity = t;
    }

    override fun clear() {
        holderActivity = null;
    }


    fun disconnect() {
        holderActivity?.getClient()?.disconnect()
    }

    override fun isAttachNow(): Boolean  = holderActivity != null
}