package com.musongzi.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.itf.holder.IHodlerViewModel
import com.musongzi.core.util.InjectionHelp
import java.lang.ref.WeakReference

class ModelFragment<V : IHodlerViewModel<*>, D : ViewDataBinding> : DataBindingFragment<D>(),
    ViewModelProvider.Factory {

    var mainViewModel: WeakReference<V?>? = null
    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null
    override fun onCreateView(
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
            mVp = ViewModelProvider(getMainViewModelProvider())
        }
        return mVp!!
    }

    protected fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val v = modelClass.newInstance()
        val vm = v as? IHodlerViewModel<*>
        vm?.let {
            it.attachNow(this)
            it.getHolderBusiness().afterHandlerBusiness()
        }
        return v;
    }

}