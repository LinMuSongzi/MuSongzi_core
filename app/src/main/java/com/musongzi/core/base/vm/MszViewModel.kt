package com.musongzi.core.base.vm

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class MszViewModel<C : IClient, B : IBusiness> : CoreViewModel<IHolderActivity>(),
    IHolderViewModel<C, B> {

    protected val TAG = javaClass.simpleName

    private var savedInstanceStateRf : WeakReference<Bundle?>? = null
    protected lateinit var business: B
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
        if (isAttachNow()) {
            return
        }
        super.attachNow(t)
        client = t?.getClient() as? C
        business = createBusiness()
        (business as? BaseLifeBusiness<IAgent>)?.setAgentModel(this)
        business.afterHandlerBusiness()
    }

    fun setHolderClient(client: C) {
        this.client = client;
    }

    override fun clear() {
        super.clear()
        client = null;
    }

    protected fun createBusiness(): B = InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()

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