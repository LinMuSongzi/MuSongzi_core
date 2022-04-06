package com.musongzi.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderDataBinding
import com.musongzi.core.util.InjectionHelp
import com.trello.rxlifecycle4.components.support.RxFragment

abstract class DataBindingFragment<D : ViewDataBinding> : RxFragment(), IHolderActivity, IDisconnect,
    IHolderDataBinding<D> {

    lateinit var dataBinding: D

    override fun getHodlerActivity(): FragmentActivity? = activity

    override fun getMainLifecycle(): IHolderActivity?   = requireActivity() as? IHolderActivity

    override fun getThisLifecycle(): LifecycleOwner?  = this

    override fun getHolderContext(): Context? = context

    override fun putArguments(d: Bundle?) {
        arguments = d;
    }


    override fun handlerArguments() {

    }

    override fun disconnect() {
        requireActivity().finish()
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

    @Deprecated("已过时", ReplaceWith("ViewDataBinding"))
    protected fun getLayoutId(): Int = 0

    protected open fun superDatabindingName(): String = DataBindingFragment::class.java.name

    protected open fun actualTypeArgumentsDatabindinIndex(): Int = 0

    override fun getHolderDataBinding(): D = dataBinding

    override fun notifyDataSetChanged() {

    }

    override fun notifyDataSetChangedItem(postiont: Int) {

    }
}