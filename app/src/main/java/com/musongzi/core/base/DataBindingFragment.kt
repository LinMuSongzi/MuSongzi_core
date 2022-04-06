package com.musongzi.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelStoreOwner
import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.holder.IHodlerActivity
import com.musongzi.core.itf.holder.IHolderDataBinding
import com.musongzi.core.util.InjectionHelp

open class DataBindingFragment<D : ViewDataBinding> : Fragment(), IHodlerActivity,
    IHolderDataBinding<D> {

    lateinit var dataBinding: D

    override fun getHodlerActivity(): FragmentActivity? = activity

    override fun getMainLifecycle(): ILifeObject   = this

    override fun getHolderContext(): Context? = context

    override fun putArguments(d: Bundle?) {
        arguments = d;
    }


    override fun handlerArguments() {

    }

    override fun getMainViewModelProvider(): ViewModelStoreOwner = this

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return instanceView(layoutInflater, container!!)
    }

    private fun instanceView(inflater: LayoutInflater, container: ViewGroup): View {
        return if (getLayoutId() == 0) {
            dataBinding = InjectionHelp.findDataBinding(
                javaClass,
                container,
                superDatabindingName(),
                actualTypeArgumentsDatabindinIndex()
            )!!
            dataBinding.root
        } else {
            inflater.inflate(getLayoutId(), container, false);
        }
    }

    private fun getLayoutId(): Int = 0

    protected fun superDatabindingName(): String = DataBindingFragment::class.java.name

    protected fun actualTypeArgumentsDatabindinIndex(): Int = 0

    override fun getHolderDataBinding(): D = dataBinding


}