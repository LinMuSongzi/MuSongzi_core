package com.musongzi.core.base.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.musongzi.core.ExtensionCoreMethod.layoutInflater
import com.musongzi.core.base.client.FragmentControlClient
import com.musongzi.core.base.client.imp.FragmentBusinessControlClientImpl
import com.musongzi.core.itf.holder.IHolderActivity
import com.musongzi.core.itf.holder.IHolderViewModelFactory
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.view.TipDialog
import com.trello.rxlifecycle4.components.support.RxFragment

/**
create by linhui , data = 2023/7/1 0:03
 **/
abstract class BaseLayoutFragment: RxFragment() , IHolderActivity,FragmentControlClient {
    protected lateinit var fControl: FragmentControlClient
    protected val TAG = javaClass.name
    private var tipDialog: Dialog? = null

    override fun showDialog(msg: String?) {
        (tipDialog ?: let {
            val t = createDialog()
            tipDialog = t;
            t
        }).show()
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fControl = FragmentBusinessControlClientImpl(this)
        return createView(inflater,container,savedInstanceState)
    }

    protected open fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getLayoutId().layoutInflater(inflater,container)
    }

    override fun onClearOperate(any: Any?): Boolean {
        return true
    }

    protected abstract fun getLayoutId(): Int


    override fun disimissDialog() {
        tipDialog?.apply {
            dismiss()
        }
    }

    override fun disconnect(): Boolean {
        return if(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && activity != null) {
            requireActivity().finish()
            true
        }else{
            false
        }
    }

    override fun fragmentControlLayoutId(): Int = 0

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


    private fun getFactoryDefaultArgs(): Bundle? {
        return arguments;
    }

    override fun topViewModelProvider(): ViewModelProvider? {
        return if (requireActivity() is IHolderViewModelProvider) {
            (requireActivity() as IHolderViewModelProvider).topViewModelProvider()
        } else {
            val factory = requireActivity() as? IHolderViewModelFactory
            ViewModelProvider(
                requireActivity(),
                (factory?.getHolderFactory() ?: newFactory(requireActivity()))
            )
        }
    }

    override fun thisViewModelProvider(): ViewModelProvider? {
        return ViewModelProvider(this, defaultViewModelProviderFactory)
    }

    protected fun newFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory =
        object : AbstractSavedStateViewModelFactory(owner, getFactoryDefaultArgs()) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return InjectionHelp.injectViewModel(
                    this@BaseLayoutFragment,
                    arguments,
                    modelClass,
                    handle
                )!!
            }
        }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return newFactory(this)
    }

    protected open fun createDialog() = TipDialog(requireActivity())

     override fun runOnUiThread(runnable: Runnable) {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            requireActivity().runOnUiThread(runnable)
        }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData();
//        initEvent()
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


    abstract fun initView()
//    abstract fun initEvent()
    abstract fun initData()

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

}