package com.mszsupport.comment

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mszsupport.itf.INotifyDataSetChanged
import com.mszsupport.util.InjectionHelp
import io.reactivex.rxjava3.core.ObservableTransformer

class FragmentViewSupportImpl<D:ViewDataBinding> (
    lifecycle: LifecycleOwner,
    savedInstanceBudnle: Bundle? = null,
    notifyDataMethod: (() -> INotifyDataSetChanged)? = null,
    observableTransformer: ObservableTransformer<*, *>? = null,
    parent: ViewGroup
) : ActivityViewSupportImpl(
    lifecycle,
    savedInstanceBudnle,
    notifyDataMethod,
    observableTransformer
) {

    init {

        lifecycle.lifecycle.addObserver(object :DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                //dataBinding = InjectionHelp.findDataBinding<D>(this.javaClass,parent,dataBindingFatherName,)
            }
        })

    }

    fun dataBindingFatherName():String{
        return "FragmentViewSupportImpl"
    }

    lateinit var dataBinding:D

}