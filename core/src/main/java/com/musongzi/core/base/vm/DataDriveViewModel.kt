package com.musongzi.core.base.vm

import android.os.Bundle
import android.util.Log
import com.musongzi.core.base.bean.BusinessInfo
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

/*** created by linhui * on 2022/9/15
 *
 * */
abstract class DataDriveViewModel<B:IBusiness> : CoreViewModel<IHolderActivity>() ,IHolderViewModel<B>,IHolderNeed{


    override fun getHolderNeed(): INeed? {
        return getHolderBusiness()
    }


    override fun showDialog(msg: String?) {

    }

    override fun disimissDialog() {

    }

    override fun getHolderClient(): IClient? {
        return null;
    }


    override fun <A> holderApiInstance(): IHolderApi<A>? {
        return this as? IHolderApi<A>;
    }

    override fun handlerSavedInstanceState(savedInstanceState: Bundle?) {
        sourceBundle = WeakReference(savedInstanceState)
    }

    override fun isSavedInstanceStateNull(): Boolean {
        return sourceBundle == null || sourceBundle?.get() == null
    }


    final override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        Log.i(TAG, "setHolderSavedStateHandle: ${javaClass.canonicalName} , " + savedStateHandle)
        super.mSavedStateHandles[REMOTE_SAVED_INDEX] = savedStateHandle
    }

    final override fun getHolderSavedStateHandle(): ISaveStateHandle {
        return mSavedStateHandles[REMOTE_SAVED_INDEX]!!
    }


    private var sourceBundle: WeakReference<Bundle?>? = null



    protected open fun createBusiness2(): B {
        TODO("Not yet implemented")
    }
    override fun getHolderBusiness(): B = business

    protected open fun indexBusinessActualTypeArgument() = 0

    override fun attachNow(t: IHolderActivity?) {
        super.attachNow(t)
        (business as? IAgentHolder<IAgent>)?.setAgentModel(this)
        business.afterHandlerBusiness()
//        handlerAnnotion(business)
    }

    private var businessInfo: BusinessInfo? = null
    private val business: B by lazy {
        createBusiness()
    }
    private fun createBusiness(): B {
        return businessInfo?.let {
            Log.i(TAG, "createBusiness: 1")
            InjectionHelp.getClassLoader().loadClass(it.className)?.newInstance() as? B
        } ?: (InjectionHelp.findGenericClass<B>(javaClass, indexBusinessActualTypeArgument()).let {
            if (it.isInterface) {
                Log.i(TAG, "createBusiness: 2")
                createBusiness2()
            } else {
                Log.i(TAG, "createBusiness: 3")
                it.newInstance()
            }
        })
    }

    override fun putArguments(d: Bundle?) {
        d?.let {
            businessInfo = d.getParcelable(InjectionHelp.BUSINESS_NAME_KEY)
        }
    }

    override fun getArguments(): Bundle? {
        return super.holderActivity?.get()?.getArguments()
    }

    override fun handlerArguments() {

    }

}