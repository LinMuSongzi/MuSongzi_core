package com.musongzi.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.musongzi.core.base.client.FragmentClient
import com.musongzi.core.base.client.FragmentControlClient
import com.musongzi.core.base.client.imp.FragmentBusinessControlClientImpl
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderDataBinding
import com.musongzi.core.itf.holder.IHolderFragmentManager
import com.musongzi.core.util.InjectionHelp
import com.trello.rxlifecycle4.components.support.RxFragment

abstract class DataBindingFragment<D : ViewDataBinding> : RxFragment(), IHolderActivity,
    IDisconnect, IHolderDataBinding<D>, FragmentControlClient,ViewModelProvider.Factory {



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.i("ViewModel", "create: "+modelClass.name)
        return savedStateViewModelFactory.create(modelClass);
    }

    lateinit var dataBinding: D

    lateinit var fControl: FragmentControlClient

    private val mMainModelProvider:ViewModelProvider by lazy {
        ViewModelProvider(requireActivity(),this)
    }
    private val mMyModelProvider:ViewModelProvider by lazy {
        ViewModelProvider(this,this)
    }

    val savedStateViewModelFactory  : SavedStateViewModelFactory by lazy{
        SavedStateViewModelFactory(null,this@DataBindingFragment)
    }

    override fun topViewModelProvider(): ViewModelProvider {
        return mMainModelProvider
    }

    override fun thisViewModelProvider(): ViewModelProvider {
        return mMyModelProvider
    }

    override fun layoutId(): Int = 0

    override fun getHolderFragmentManager() = childFragmentManager

    override fun getHolderParentFragmentManager(): FragmentManager? = parentFragmentManager

//    override fun getNextByClass(nextClass: Class<*>): IClient?  = nul

    override fun getHolderActivity(): FragmentActivity? = activity

    override fun getMainLifecycle(): IHolderActivity? = requireActivity() as? IHolderActivity

    override fun getThisLifecycle(): LifecycleOwner? = this

    override fun getHolderContext(): Context? = context

    override fun putArguments(d: Bundle?) {
        arguments = d;
    }


    override fun handlerArguments() {

    }

    override fun disconnect() {
        requireActivity().finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fControl = FragmentBusinessControlClientImpl(this)
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


    override fun addFragment(fragment: Fragment, tag: String?, isHide: Boolean) {
        fControl.addFragment(fragment, tag, isHide)
    }

    override fun <F : Fragment> addFragment(
        fragmentClass: Class<F>,
        tag: String?,
        isHide: Boolean
    ) {
        fControl.addFragment(fragmentClass, tag, isHide)
    }

    override fun replaceFragment(fragment: Fragment, tag: String?, isHide: Boolean) {
        fControl.replaceFragment(fragment, tag, isHide)
    }

    override fun <F : Fragment> replaceFragment(
        fragmentClass: Class<F>,
        tag: String?,
        isHide: Boolean
    ) {
        fControl.replaceFragment(fragmentClass, tag, isHide)
    }

    override fun removeFragment(tag: String) {
        fControl.removeFragment(tag)
    }

    override fun removeFragment(fragment: Fragment) {
        fControl.removeFragment(fragment)
    }

    override fun <F : Fragment> removeFragment(fragmentClass: Class<F>) {
        fControl.removeFragment(fragmentClass)
    }

    override fun getFragmentByTag(tag: String): Fragment? = fControl.getFragmentByTag(tag)
}