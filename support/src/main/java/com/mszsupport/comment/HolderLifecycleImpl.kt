package com.mszsupport.comment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mszsupport.itf.IViewInstance
import com.mszsupport.itf.holder.IHolderLifecycle
import java.lang.ref.WeakReference

class HolderLifecycleImpl(l: LifecycleOwner) : IHolderLifecycle,IViewInstance by ViewInstanceImpl(l) {

    var lifecycle = WeakReference(l)

    override fun getMainLifecycle(): IHolderLifecycle? {
        return if (lifecycle.get() is FragmentActivity) {
            this
        } else {
            (lifecycle.get() as? Fragment)?.let {
                HolderLifecycleImpl(it.requireActivity())
            }
        }
    }

    override fun getThisLifecycle(): LifecycleOwner? {
        return lifecycle.get()
    }

}