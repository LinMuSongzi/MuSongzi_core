package com.musongzi.core.base.vm

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.util.UiUtil
import com.trello.rxlifecycle4.LifecycleTransformer
import java.lang.ref.WeakReference

abstract class CoreViewModel<H : IHolderActivity> : ViewModel(), IAttach<H>, IWant, IDisconnect {
    protected val TAG = javaClass.simpleName
    companion object {
        const val LOCAL_SAVED_INDEX = 1;
        const val REMOTE_SAVED_INDEX = 0;
        const val SAVEDS_MAX = LOCAL_SAVED_INDEX + REMOTE_SAVED_INDEX + 1
    }

    override fun runOnUiThread(runnable: Runnable) {
        UiUtil.post(runnable)
    }

    protected var holderActivity: WeakReference<IHolderActivity?>? = null

    protected var mSavedStateHandles = Array<ISaveStateHandle?>(SAVEDS_MAX) {
        null
    }

    fun localSavedStateHandle(): ISaveStateHandle {
        return mSavedStateHandles[LOCAL_SAVED_INDEX] ?: LocalSavedHandler().apply {
            mSavedStateHandles[LOCAL_SAVED_INDEX] = this
        }
    }

    @Deprecated("已过期", replaceWith = ReplaceWith("建议使用,ISaveStateHandle 来观察"))
    override fun attachNow(t: H?) {
        Log.i(TAG, "attachNow: = $t")
        holderActivity = WeakReference(t);
    }

    override fun clear() {
        holderActivity = null;
    }

    final override fun disconnect(): Boolean {
        return holderActivity?.get()?.getClient()?.disconnect() ?: true
    }

    final override fun isAttachNow(): Boolean = holderActivity != null

    override fun <T> bindToLifecycle(): LifecycleTransformer<T>? {
        return holderActivity?.get()?.bindToLifecycle()
    }

    final override fun getMainLifecycle(): IHolderLifecycle? = holderActivity?.get()?.getMainLifecycle()

    final override fun getThisLifecycle(): LifecycleOwner? = holderActivity?.get()?.getThisLifecycle()

//    companion object{
//        const val
//    }

}