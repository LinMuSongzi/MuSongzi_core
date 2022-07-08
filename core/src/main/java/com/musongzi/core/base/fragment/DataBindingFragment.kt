package com.musongzi.core.base.fragment

import android.app.Activity
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
import androidx.savedstate.SavedStateRegistryOwner
import com.musongzi.core.base.client.FragmentClient
import com.musongzi.core.base.client.FragmentControlClient
import com.musongzi.core.base.client.imp.FragmentBusinessControlClientImpl
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderDataBinding
import com.musongzi.core.itf.holder.IHolderFragmentManager
import com.musongzi.core.itf.holder.IHolderViewModel
import com.musongzi.core.util.InjectionHelp
import com.trello.rxlifecycle4.components.support.RxFragment

abstract class DataBindingFragment<D : ViewDataBinding> : RxFragment(), IHolderActivity,
    IDisconnect, IHolderDataBinding<D>, FragmentControlClient {

    protected var savedInstance :Bundle? = null

    protected val TAG = javaClass.name

    lateinit var dataBinding: D

    private lateinit var fControl: FragmentControlClient

    private var mMainModelProvider: ViewModelProvider? = null

    private fun newFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory =
        object : AbstractSavedStateViewModelFactory(owner, getFactoryDefaultArgs()) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val t = modelClass.newInstance()
                (t as? IHolderViewModel<*, *>)?.let {
                    if (it.getHolderSavedStateHandle() == null) {
                        it.setHolderSavedStateHandle(handle)
                    }
                    create(it)
                }
                return t;
            }
        }

    protected abstract fun create(vm: IHolderViewModel<*, *>?)

    private fun getFactoryDefaultArgs(): Bundle? {
        return null;
    }

    override fun topViewModelProvider(): ViewModelProvider {
        val m: ViewModelProvider
        if (this.mMainModelProvider == null) {
            m = ViewModelProvider(requireActivity(), newFactory(requireActivity()))
            this.mMainModelProvider = m;
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    mMainModelProvider = null
                }
            })
        } else {
            m = this.mMainModelProvider!!
        }
        return m
    }

    override fun thisViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, newFactory(this))
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
        Log.i(TAG, "FragmentState:onCreateView")
        fControl = FragmentBusinessControlClientImpl(this)
        return instanceView(layoutInflater, container!!)
    }

    private fun instanceView(inflater: LayoutInflater, container: ViewGroup): View {
        return if (getLayoutId() == 0) {
            Log.i(TAG, "FragmentState:instanceView findDataBinding")
            if (view == null) {
                dataBinding = InjectionHelp.findDataBinding(
                    javaClass,
                    container,
                    superDatabindingName(),
                    actualTypeArgumentsDatabindinIndex()
                )!!
            }
            dataBinding.root

        } else {
            Log.i(TAG, "FragmentState:instanceView inflate layout")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "FragmentState:onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "FragmentState:onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "FragmentState:onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "FragmentState:onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "FragmentState:onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "FragmentState:onDestory")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "FragmentState:onDestoryView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "FragmentState:onDetach")
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        Log.i(TAG, "FragmentState:onAttach(activity:$activity)")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "FragmentState:onAttach(context:$context)")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, "FragmentState:onHiddenChange($hidden)")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.i(TAG, "FragmentState:onLowMemory")
    }

//    on



//    open class NativeSimpleFactory(owner: SavedStateRegistryOwner, defaultArgs: Bundle?) :
//        AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//
//        override fun <T : ViewModel?> create(
//            key: String,
//            modelClass: Class<T>,
//            handle: SavedStateHandle
//        ): T {
//            TODO("Not yet implemented")
//        }
//
//    }

}