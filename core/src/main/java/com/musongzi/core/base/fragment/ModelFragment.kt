package com.musongzi.core.base.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.holder.IHolderViewModel
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.view.TipDialog

abstract class ModelFragment<V : IHolderViewModel<*, *>, D : ViewDataBinding> :
    DataBindingFragment<D>() {

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
        savedInstance = savedInstanceState;
        arguments?.let {
            modelProviderEnable = it.getInt(PROVIDER_MODEL_KEY, PROVIDER_NORMAL)
        }
        if (CLASS_CACHE[javaClass.name] == null) {
            val c = arrayOfNulls<Class<*>>(1)
            instanceViewModel(c);
            CLASS_CACHE[javaClass.name] = c[0]
        }
        Log.i(TAG, "onCreateView: viewModel = ${getViewModel()}")
        handlerArguments()
        return v;
    }

    fun getViewModel(): V {
        return InjectionHelp.getViewModel(getProvider(), CLASS_CACHE[javaClass.name])
    }

    override fun onDestroy() {
        super.onDestroy()
        savedInstance = null
    }

    override fun actualTypeArgumentsDatabindinIndex() = 1

    protected open fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun superDatabindingName() = ModelFragment::class.java.name

    protected open fun instanceViewModel(clazz: Array<Class<*>?>): V? = InjectionHelp.findViewModel(
        javaClass,
        superFragmentName(),
        getProvider(),
        actualTypeArgumentsViewModelIndex(),
        clazz
    )

    protected open fun superFragmentName(): String = ModelFragment::class.java.name

    private fun getProvider(): ViewModelProvider {
        val p: ViewModelProvider
        when {
            modelProviderEnable.and(PROVIDER_MAIN) > 0 -> {
                p = topViewModelProvider()
            }
            modelProviderEnable.and(PROVIDER_SINGLE) > 0 -> {
                p = thisViewModelProvider()
            }
            else -> {
                modelProviderEnable =
                    modelProviderEnable.or(if (isSingleViewModelProvider()) PROVIDER_SINGLE else PROVIDER_MAIN)
                return getProvider()
            }
        }

        return p
    }

    protected open fun isSingleViewModelProvider(): Boolean {
        return false
    }

//    private fun getMainViewProvider(): ViewModelProvider {
//        if (mVp == null) {
//            mVp = ViewModelProvider(getMainViewModelProvider(), this)
//        }
//        return mVp!!
//    }
//
//    private fun getThisViewProvider(): ViewModelProvider {
//        if (vp == null) {
//            vp = ViewModelProvider(this, this)
//        }
//        return vp!!
//    }


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


//    override fun create(vm: IHolderViewModel<*, *>?) {
//        InjectionHelp.injectViewModel(this,savedInstance,vm)
//    }


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

        @Deprecated("暂时不理")
        val CLASS_CACHE = HashMap<String, Class<*>?>()

        /**
         * 1
         */
        const val PROVIDER_NORMAL = 1

        /**
         * 2
         */
        const val PROVIDER_SINGLE = PROVIDER_NORMAL.shl(1);

        /**
         * 4
         */
        const val PROVIDER_MAIN = PROVIDER_NORMAL.shl(2);

        fun composeProvider(b: Bundle?, flag: Boolean) {
            b?.putInt(PROVIDER_MODEL_KEY, if (flag) PROVIDER_MAIN else PROVIDER_SINGLE)
        }

    }

}