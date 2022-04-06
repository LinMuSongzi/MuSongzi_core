package com.musongzi.core.base

import androidx.lifecycle.ViewModel
import com.musongzi.core.itf.IAttach
import com.musongzi.core.itf.holder.IHodlerActivity
import com.musongzi.core.itf.holder.IHodlerClient

abstract class CoreViewModel<H : IHodlerActivity> : ViewModel(), IAttach<H> {

    protected var holderActivity: IHodlerActivity? = null

    override fun attachNow(t: H?) {
        holderActivity = t;
    }

    override fun clear() {
        holderActivity = null;
    }

    override fun isAttachNow(): Boolean  = holderActivity != null
}