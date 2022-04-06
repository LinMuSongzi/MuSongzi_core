package com.musongzi.core.base

import androidx.lifecycle.Lifecycle
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp

open class MszViewModel<C : IClient, B : IBusiness> : CoreViewModel<IHodlerActivity>(),
    IHodlerViewModel<C, B> {

    private lateinit var business: B
    private var client: C? = null

    override fun getMainLifecycle(): ILifeObject? = super.holderActivity?.getMainLifecycle()

    override fun getThisLifecycle(): Lifecycle? = super.holderActivity?.getThisLifecycle()

    override fun showDialog(msg: String?) {
        client?.showDialog(msg)
    }

    override fun disimissDialog() {
        client?.disimissDialog()
    }


    override fun attachNow(t: IHodlerActivity?) {
        if (isAttachNow()) {
            return
        }
        super.attachNow(t)
        client = t?.getClient() as? C
        business = createBusiness()
        (business as? BaseLifeBusiness<IAgent>)?.setAgentModel(this)
        business.afterHandlerBusiness()
    }

    fun setClient(client: C) {
        this.client = client;
    }

    override fun clear() {
        super.clear()
        client = null;
    }

    protected fun createBusiness(): B = InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()

    override fun getHolderBusiness(): B = business

    override fun getHolderClient(): C? = client

}