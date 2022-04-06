package com.musongzi.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHodlerViewModel
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

abstract class ModelFragment<V : IHodlerViewModel<*,*>, D : ViewDataBinding> : DataBindingFragment<D>(),
    ViewModelProvider.Factory ,IClient{

    var mainViewModel: WeakReference<V?>? = null
    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        mainViewModel = instanceViewModel();
        return v;
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData();
        initEvent()
    }
    abstract fun initView()
    abstract fun initEvent()
    abstract fun initData()

    protected fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val v = modelClass.newInstance()
        val vm = v as? IHodlerViewModel<*,*>
        vm?.let {
            it.attachNow(this)
        }
        return v;
    }

    override fun getClient(): IClient = this


    override fun showDialog(msg: String?) {

    }

    override fun disimissDialog() {

    }

}