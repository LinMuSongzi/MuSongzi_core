package com.musongzi.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderViewModel
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class ModelFragment<V : IHolderViewModel<*,*>, D : ViewDataBinding> : DataBindingFragment<D>(),
    ViewModelProvider.Factory ,IClient{
    protected val TAG = javaClass.simpleName
    private var viewModel: WeakReference<V?>? = null
    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        viewModel = instanceViewModel();
        return v;
    }

    override fun actualTypeArgumentsDatabindinIndex() = 1

    override fun superDatabindingName() = ModelFragment::class.java.name

    fun getMainViewModel():V{
        return viewModel?.get()!!
    }

    protected fun instanceViewModel(): WeakReference<V?>? = InjectionHelp.findViewModel(
        javaClass,
        getMainViewProvider(),
        actualTypeArgumentsViewModelIndex()
    )

    private fun getMainViewProvider(): ViewModelProvider {
        if (mVp == null) {
            mVp = ViewModelProvider(getMainViewModelProvider(),this)
        }
        return mVp!!
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.get()?.handlerSavedInstanceState(savedInstanceState)
        initView()
        initData();
        initEvent()
    }
    abstract fun initView()
    abstract fun initEvent()
    abstract fun initData()

    protected fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val v = modelClass.newInstance()
        val vm = v as? IHolderViewModel<*,*>
        vm?.let {
            it.attachNow(this)
            it.putArguments(arguments)
            it.handlerArguments()
        }
        return v;
    }



    override fun getClient(): IClient = this


    override fun showDialog(msg: String?) {

    }

    override fun disimissDialog() {

    }






}