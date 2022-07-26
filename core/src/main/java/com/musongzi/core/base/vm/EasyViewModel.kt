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

abstract class EasyViewModel<C : IClient?, B : IBusiness>() : CoreViewModel<IHolderActivity>(),
    IHolderViewModel<C, B> {

    protected val TAG = javaClass.simpleName

    override fun <A> holderApiInstance(): IHolderApi<A>? {
        return this as? IHolderApi<A>;
    }

    private var businessInfo: BusinessInfo? = null

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

    private fun createBusiness(): B {
        return businessInfo?.let {
            Log.i(TAG, "createBusiness: 1")
            InjectionHelp.getClassLoader().loadClass(it.className)?.newInstance() as? B
        } ?: (InjectionHelp.findGenericClass<B>(javaClass, indexBusinessActualTypeArgument()).let {
            if(it.isInterface){
                Log.i(TAG, "createBusiness: 2")
                createBusiness2()
            }else{
                Log.i(TAG, "createBusiness: 3")
                it.newInstance()
            }
        })

//       return if(businessInfo == null) {
//            InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()
//        }else{
//           val b =  businessInfo?.let {
//                BusinessInfo::class.java.classLoader?.loadClass(it.className)?.newInstance() as? B
//            }
//           b ?: InjectionHelp.findGenericClass<B>(javaClass, 1).newInstance()
//        }
    }

    protected open fun createBusiness2(): B {
        TODO("Not yet implemented")
    }

    private fun indexBusinessActualTypeArgument() = 1


    override fun getHolderBusiness(): B = business

    override fun getHolderClient(): C? {
        return InjectionHelp.checkClient(client, javaClass,indexClientActualTypeArgument())
    }

    protected fun indexClientActualTypeArgument(): Int = 0;

    override fun handlerSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceStateRf = WeakReference(savedInstanceState)
    }

    override fun isSavedInstanceStateNull(): Boolean {
        return savedInstanceStateRf == null || savedInstanceStateRf?.get() == null
    }

    override fun putArguments(d: Bundle?) {
        d?.let {
            businessInfo = d.getParcelable(InjectionHelp.BUSINESS_NAME_KEY)
        }
    }

    override fun getArguments(): Bundle? {
        return super.holderActivity?.getArguments()
    }

    override fun handlerArguments() {

    }

}