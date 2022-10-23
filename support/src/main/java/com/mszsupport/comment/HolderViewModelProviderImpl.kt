package com.mszsupport.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.mszsupport.itf.IActivityView
import com.mszsupport.itf.holder.IHolderViewModel
import com.mszsupport.itf.holder.IHolderViewModelFactory
import com.mszsupport.itf.holder.IHolderViewModelProvider
import java.lang.ref.WeakReference

class HolderViewModelProviderImpl(
    var activity: IActivityView,
    savedInstanceBundle: Bundle? = null,
    factory: IHolderViewModelFactory? = null
) : IHolderViewModelProvider {

    private var mainpProvider: WeakReference<ViewModelProvider?>? = null
    private var thisProvider: ViewModelProvider? = null

    init {
        val f = factory?.getHolderFactory() ?: object : AbstractSavedStateViewModelFactory(
            activity.getThisLifecycle() as SavedStateRegistryOwner,
            savedInstanceBundle
        ) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val t = modelClass.newInstance()
                (t as? IHolderViewModel<*>)?.apply {
                    t.attachNow(activity)
                    setHolderSavedStateHandle(SaveStateHandleWarp(handle))
                }
                return t

            }
        }
        activity.getThisLifecycle()?.let {
            if (it is FragmentActivity) {
                thisProvider = ViewModelProvider(it as ViewModelStoreOwner, f)
                mainpProvider = WeakReference(thisProvider)
            } else {
                thisProvider = ViewModelProvider(it as ViewModelStoreOwner, f)
                mainpProvider = WeakReference(
                    ViewModelProvider(
                        (it as Fragment).requireActivity() as ViewModelStoreOwner,
                        f
                    )
                )
            }
        }
    }

    override fun topViewModelProvider(): ViewModelProvider? {
        return mainpProvider?.get()
    }

    override fun thisViewModelProvider(): ViewModelProvider? {
        return thisProvider
    }
}