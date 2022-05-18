package com.musongzi.core.base.fragment

import android.app.Dialog
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
import com.musongzi.core.view.TipDialog
import java.lang.ref.WeakReference

abstract class ModelFragment<V : IHolderViewModel<*, *>, D : ViewDataBinding> :
    DataBindingFragment<D>(),
    ViewModelProvider.Factory {
    protected val TAG = javaClass.simpleName
    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null
    private var tipDialog: Dialog? = null
    private var modelProviderEnable = PROVIDER_NORMAL;

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        arguments?.let {
            modelProviderEnable = it.getInt(PROVIDER_MODEL_KEY, PROVIDER_NORMAL)
        }
        instanceViewModel();
        Log.i(TAG, "onCreateView: viewModel = ${getViewModel()}")
        Log.i(TAG, "onCreateView: viewModel = ${getViewModel()}\n")
        handlerArguments()
        return v;
    }

    fun getViewModel(): V {
        return InjectionHelp.getViewModel(getProvider(), CLASS_CACHE[0])
    }

    override fun actualTypeArgumentsDatabindinIndex() = 1

    protected open fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun superDatabindingName() = ModelFragment::class.java.name

    protected open fun instanceViewModel(): V? = InjectionHelp.findViewModel(
        javaClass,
        superViewModelName(),
        getProvider(),
        actualTypeArgumentsViewModelIndex(),
        if (CLASS_CACHE[0] == null) CLASS_CACHE else null
    )

    protected open fun superViewModelName(): String = ModelFragment::class.java.name

    private fun getProvider(): ViewModelProvider {
        val p: ViewModelProvider
        when {
            modelProviderEnable.and(PROVIDER_MAIN) > 0 -> {
                p = getMainViewProvider()
            }
            modelProviderEnable.and(PROVIDER_SINGLE) > 0 -> {
                p = getThisViewProvider()
            }
            else -> {
                modelProviderEnable = modelProviderEnable.or(if (isSingleViewModelProvider()) PROVIDER_MAIN else PROVIDER_SINGLE)
                return getProvider()
            }
        }

        return p
    }

    protected open fun isSingleViewModelProvider(): Boolean {
        return true
    }

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().handlerSavedInstanceState(savedInstanceState)
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
        (tipDialog ?: let {
            val t = createDialog()
            tipDialog = t;
            t
        }).show()
    }

    override fun disimissDialog() {
        tipDialog?.apply {
            dismiss()
        }
    }

    protected open fun createDialog() = TipDialog(requireActivity())

    companion object {
        const val PROVIDER_MODEL_KEY = "provider_model_key"

        val CLASS_CACHE = arrayOfNulls<Class<*>>(1)

        const val PROVIDER_NORMAL = 1
        const val PROVIDER_SINGLE = PROVIDER_NORMAL.shl(1);
        const val PROVIDER_MAIN = PROVIDER_NORMAL.shl(2);

        fun composeProvider(b: Bundle?, flag: Boolean) {
            b?.putBoolean(PROVIDER_MODEL_KEY, flag)
        }

    }

}