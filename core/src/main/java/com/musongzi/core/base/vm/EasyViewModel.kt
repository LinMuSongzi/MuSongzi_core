package com.musongzi.core.base.vm

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class EasyViewModel<C : IClient, B : IBusiness>() : CoreViewModel<IHolderActivity>(),
    IHolderViewModel<C, B> {

    protected val TAG = javaClass.simpleName

//    constructor(savedStateHandle: SavedStateHandle) : this() {
//        setHolderSavedStateHandle(savedStateHandle)
//    }

    final override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        Log.i(TAG, "setHolderSavedStateHandle: ${javaClass.canonicalName} , " + savedStateHandle)
        super.mSavedStateHandle = savedStateHandle
    }

    final override fun getHolderSavedStateHandle(): ISaveStateHandle {
        return mSavedStateHandle
    }

    private var savedInstanceStateRf: WeakReference<Bundle?>? = null
    val business: B by lazy {
        createBusiness()
    }
    protected var client: C? = null

    override fun getMainLifecycle(): IHolderLifecycle? = super.holderActivity?.getMainLifecycle()

    override fun getThisLifecycle(): LifecycleOwner? = super.holderActivity?.getThisLifecycle()


    override fun showDialog(msg: String?) {
        client?.showDialog(msg)
    }

    override fun disimissDialog() {
        client?.disimissDialog()
    }


    override fun attachNow(t: IHolderActivity?) {
        synchronized(this) {
            if (isAttachNow()) {
                return
            }
            super.attachNow(t)
            client = t?.getClient() as? C
            (business as? IAgentHolder<IAgent>)?.setAgentModel(this)
            business.afterHandlerBusiness()
        }
    }

    @Deprecated("置换V层Client，不建议使用", ReplaceWith("this.client = client"))
    fun setHolderClient(client: C) {
        this.client = client;
    }

    override fun clear() {
        super.clear()
        client = null;
    }

    protected fun createBusiness(): B =
        InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()

    override fun getHolderBusiness(): B = business

    override fun getHolderClient(): C? = client

    override fun handlerSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceStateRf = WeakReference(savedInstanceState)
    }

    override fun isSavedInstanceStateNull(): Boolean {
        return savedInstanceStateRf == null
    }

    override fun putArguments(d: Bundle?) {

    }

    override fun getArguments(): Bundle? {
        return super.holderActivity?.getArguments()
    }

    override fun handlerArguments() {

    }

}