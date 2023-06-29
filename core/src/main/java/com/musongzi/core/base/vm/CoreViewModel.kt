package com.musongzi.core.base.vm

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.itf.*
import com.musongzi.core.itf.client.IContextClient
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.holder.IHolderLocaSavedStateHandler
import com.musongzi.core.util.UiUtil
import com.trello.rxlifecycle4.LifecycleTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.ref.WeakReference

abstract class CoreViewModel : ViewModel(), IAttach<IContextClient>, IWant, IDisconnect, IHolderLocaSavedStateHandler {
    protected val TAG = javaClass.simpleName

    companion object {
        const val LOCAL_SAVED_INDEX = 0;
        const val REMOTE_SAVED_INDEX = 1;
        const val SAVEDS_MAX = LOCAL_SAVED_INDEX + REMOTE_SAVED_INDEX + 1
    }

    override fun runOnUiThread(runnable: Runnable) {
        UiUtil.post(runnable = runnable)
    }

//    override fun getHolderCoLifeCycle(): CoLifeCycle? {
//        return this
//    }


    /**
     * 有可能为空，如果是默认factory 注入的话
     * 不会走attachNow（）
     */
    @Deprecated("过期，不安全")
    protected var holderActivity: WeakReference<IContextClient?>? = null
//    private val coLifeCycleImpl by lazy {
//        VmCoLifeCycle()
//    }
    protected var mSavedStateHandles = arrayOfNulls<ISaveStateHandle?>(SAVEDS_MAX)

    override val localSavedStateHandle: ISaveStateHandle
        get() = mSavedStateHandles[LOCAL_SAVED_INDEX] ?: LocalSavedHandler().apply { mSavedStateHandles[LOCAL_SAVED_INDEX] = this }

//    override fun dispoasble(disposable: Disposable?) {
//        coLifeCycleImpl.dispoasble(disposable)
//    }

    override fun onCleared() {
        holderActivity = null;
//        coLifeCycleImpl.clearNow()
    }

    @Deprecated("")
    override fun attachNow(t: IContextClient?) {
        Log.i(TAG, "attachNow: = $t")
        t?.apply {
            holderActivity = WeakReference(this)
        }
    }

    final override fun disconnect(): Boolean {
        return holderActivity?.get()?.disconnect() ?: true
    }

    final override fun isAttachNow(): Boolean = holderActivity != null

    override fun <T> bindToLifecycle(): LifecycleTransformer<T>? {
        return (holderActivity?.get() as? IWant)?.bindToLifecycle()
    }

    final override fun getMainLifecycle(): IHolderLifecycle? = (holderActivity?.get() as? IHolderLifecycle)?.getMainLifecycle()

    final override fun getThisLifecycle(): LifecycleOwner? = (holderActivity?.get() as? IHolderLifecycle)?.getThisLifecycle()


}