package com.musongzi.core.base.vm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.base.bean.BusinessInfo
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class MszViewModel<C : IClient?, B : IBusiness>() : DataDriveViewModel<B>(),
    IHolderClientViewModel<C, B> {

//    protected var client: C? = null


//    override fun showDialog(msg: String?) {
//        holderActivity?.getClient()?.showDialog(msg)
//    }
//
//    override fun disimissDialog() {
//        holderActivity?.getClient()?.disimissDialog()
//    }


    override fun attachNow(t: IHolderActivity?) {
        synchronized(this) {
            if (isAttachNow()) {
                return
            }

//            client = t?.getClient() as? C
            super.attachNow(t)
        }
    }

    @Deprecated("置换V层Client，不建议使用", ReplaceWith("this.client = client"))
    final fun setHolderClient(client: C) {
//        this.client = client;
    }

    override fun clear() {
        super.clear()
    }

    override fun indexBusinessActualTypeArgument() = 1

    override fun getHolderClient(): C? {
        return InjectionHelp.checkClient(holderActivity?.get()?.getClient() as? C, javaClass, indexClientActualTypeArgument())
    }

    protected fun indexClientActualTypeArgument(): Int = 0;





}