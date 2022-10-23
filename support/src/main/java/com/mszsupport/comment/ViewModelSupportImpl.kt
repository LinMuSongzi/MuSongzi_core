package com.mszsupport.comment

import androidx.lifecycle.LifecycleOwner
import com.mszsupport.itf.IBusiness
import com.mszsupport.itf.IClient
import com.mszsupport.itf.ISaveStateHandle
import com.mszsupport.itf.IActivityView
import com.mszsupport.itf.holder.IHolderApi
import com.mszsupport.itf.holder.IHolderLifecycle
import com.mszsupport.itf.holder.IHolderViewModel
import java.lang.ref.WeakReference

class ViewModelSupportImpl<B : IBusiness>() : IHolderViewModel<B> {

    private var localSavedHandler: ISaveStateHandle? = null
    private lateinit var savedStateHandler: ISaveStateHandle
    private var hodlerActivity: WeakReference<IActivityView?>? = null
    private lateinit var business: B

    fun getLocalSavedHandler(): ISaveStateHandle {
        if (localSavedHandler == null) {
            localSavedHandler = LocalSavedHandler()
        }
        return localSavedHandler!!
    }

    override fun getHolderClient(): IClient? {
        return hodlerActivity?.get()?.getClient()
    }

    override fun <Api> holderApiInstance(): IHolderApi<Api>? {
        TODO("Not yet implemented")
    }

    override fun getMainLifecycle(): IHolderLifecycle? = hodlerActivity?.get()?.getMainLifecycle()


    override fun getThisLifecycle(): LifecycleOwner? = hodlerActivity?.get()?.getThisLifecycle()

    override fun runOnUiThread(runnable: Runnable) {
        hodlerActivity?.get()?.runOnUiThread(runnable)
    }

    override fun showDialog(msg: String?) {
        hodlerActivity?.get()?.showDialog(msg)
    }

    override fun disimissDialog() {
        hodlerActivity?.get()?.disconnect()
    }

    override fun attachNow(t: IActivityView?) {
        if(isAttachNow()){
            return
        }
        hodlerActivity = WeakReference(t)
    }

    override fun clear() {
        hodlerActivity = null
    }

    override fun isAttachNow(): Boolean = hodlerActivity?.get() != null


    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        savedStateHandler = savedStateHandle;
    }

    override fun getHolderSavedStateHandle(): ISaveStateHandle = savedStateHandler

    override fun getSavedState(): ISaveStateHandle = getLocalSavedHandler()

    override fun getHolderBusiness(): B = business

}