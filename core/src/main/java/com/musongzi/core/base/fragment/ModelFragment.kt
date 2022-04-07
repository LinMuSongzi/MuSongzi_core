package com.musongzi.core.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.vm.CoreViewModel
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderViewModel
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class ModelFragment<V : IHolderViewModel<*, *>, D : ViewDataBinding> : DataBindingFragment<D>(),
    ViewModelProvider.Factory, IClient {
    protected val TAG = javaClass.simpleName
    private var viewModel: V? = null
    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        viewModel = instanceViewModel();
        Log.i(TAG, "onCreateView: viewModel = $viewModel")
        Log.i(TAG, "onCreateView: viewModel = ${viewModel?.getHolderBusiness()}\n")
        return v;
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
    }

    override fun actualTypeArgumentsDatabindinIndex() = 1
    protected open fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun superDatabindingName() = ModelFragment::class.java.name

    fun getMainViewModel(): V? {
        return viewModel
    }

    protected open fun instanceViewModel(): V? = InjectionHelp.findViewModel(
        javaClass,
        superViewModelName(),
        getProvider(),
        actualTypeArgumentsViewModelIndex()
    )

    protected open fun superViewModelName(): String = ModelFragment::class.java.name

    private fun getProvider(): ViewModelProvider = arguments?.let {
        if (it.getBoolean(PROVIDER_MODEL_KEY, true)) {
            getMainViewProvider()
        } else {
            getThisViewProvider()
        }
    } ?: getMainViewProvider()

    private fun getMainViewProvider(): ViewModelProvider {
        if (mVp == null) {
            mVp = ViewModelProvider(getMainViewModelProvider(), this)
        }
        return mVp!!
    }

    private fun getThisViewProvider(): ViewModelProvider {
        if (vp == null) {
            vp = ViewModelProvider(this, this)
        }
        return vp!!
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.handlerSavedInstanceState(savedInstanceState)
        initView()
        initData();
        initEvent()
    }

    abstract fun initView()
    abstract fun initEvent()
    abstract fun initData()


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val v = modelClass.newInstance()
        val vm = v as? IHolderViewModel<*, *>
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

    companion object {
        const val PROVIDER_MODEL_KEY = "provider_model_key"

        fun composeProvider(b: Bundle?, flag: Boolean) {
            b?.putBoolean(PROVIDER_MODEL_KEY, flag)
        }

    }

}