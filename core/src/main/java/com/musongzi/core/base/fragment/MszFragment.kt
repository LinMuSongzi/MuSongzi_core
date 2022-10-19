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
import com.musongzi.core.itf.holder.IHolderViewModel
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.view.TipDialog

/**
 * 有videmodel
 */
abstract class MszFragment<V : IHolderViewModel<*>, D : ViewDataBinding> :
    DataBindingFragment<D>() {

    private var mVp: ViewModelProvider? = null
    private var vp: ViewModelProvider? = null

    private var modelProviderEnable = PROVIDER_NORMAL;

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
//        savedInstance = savedInstanceState;
        arguments?.let {
            modelProviderEnable = it.getInt(PROVIDER_MODEL_KEY, PROVIDER_NORMAL)
        }
        if (CLASS_CACHE[javaClass.name] == null) {
            val c = arrayOfNulls<Class<*>>(1)
            instanceViewModel(c);
            CLASS_CACHE[javaClass.name] = c[0]
            Log.i(TAG, "onCreateView: find viewModel")
        }else {
            Log.i(TAG, "onCreateView: cache had viewModel = ${getViewModel()}")
        }
        handlerArguments()
        return v;
    }

    fun getViewModel(): V {
        return InjectionHelp.getViewModel(getProvider()!!, CLASS_CACHE[javaClass.name])
    }

    fun getViewModelOrNull(): V? {
        val p = getProvider()
        return if (p == null) {
            null
        } else {
            InjectionHelp.getViewModel(p, CLASS_CACHE[javaClass.name])
        }
    }

    override fun actualTypeArgumentsDatabindinIndex() = 1

    protected open fun actualTypeArgumentsViewModelIndex(): Int = 0

    override fun superDatabindingName() = MszFragment::class.java.name

    protected open fun instanceViewModel(clazz: Array<Class<*>?>): V? = InjectionHelp.findViewModel(
        javaClass,
        superFragmentName(),
        getProvider()!!,
        actualTypeArgumentsViewModelIndex(),
        clazz
    )

    protected open fun superFragmentName(): String = MszFragment::class.java.name

    private fun getProvider(): ViewModelProvider? {
        val p: ViewModelProvider?
        when {
            modelProviderEnable.and(PROVIDER_MAIN) == PROVIDER_MAIN -> {
                p = topViewModelProvider()
            }
            modelProviderEnable.and(PROVIDER_SINGLE) == PROVIDER_SINGLE -> {
                p = thisViewModelProvider()
            }
            else -> {
                modelProviderEnable.and(PROVIDER_NORMAL.inv())
                modelProviderEnable = modelProviderEnable.or(if (isNeedTopViewModelProvider()) PROVIDER_MAIN else PROVIDER_SINGLE)
                return getProvider()
            }
        }
        Log.i(TAG, "getProvider: $modelProviderEnable")
        return p
    }

    protected open fun isNeedTopViewModelProvider(): Boolean {
        return false
    }


    override fun handlerOnViewCreateSaveInstanceState(savedInstanceState: Bundle?) {
        super.handlerOnViewCreateSaveInstanceState(savedInstanceState)
        getViewModel().handlerSavedInstanceState(savedInstanceState)
    }


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