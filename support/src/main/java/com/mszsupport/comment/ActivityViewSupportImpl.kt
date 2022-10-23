package com.mszsupport.comment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.mszsupport.itf.IClient
import com.mszsupport.itf.INotifyDataSetChanged
import com.mszsupport.itf.IActivityView
import com.mszsupport.itf.holder.IHolderLifecycle
import com.mszsupport.itf.holder.IHolderViewModelProvider
import io.reactivex.rxjava3.core.ObservableTransformer

class ActivityViewSupportImpl @JvmOverloads constructor(
    var lifecycle: LifecycleOwner,
    savedInstanceBudnle: Bundle?,
    notifyDataMethod: (() -> INotifyDataSetChanged)? = null,
    var observableTransformer: ObservableTransformer<*, *>? = null
) : IActivityView, IClient,
    IHolderLifecycle by HolderLifecycleImpl(lifecycle), IHolderViewModelProvider,
    INotifyDataSetChanged {

    var notifyData = notifyDataMethod?.invoke()
    var miHolderViewModelProviderImpl = HolderViewModelProviderImpl(this, savedInstanceBudnle)

    override fun notifyDataSetChanged() {
        notifyData?.notifyDataSetChanged()
    }

    override fun notifyDataSetChangedItem(postiont: Int) {
        notifyData?.notifyDataSetChangedItem(postiont)
    }

    override fun showDialog(msg: String?) {
        notifyData?.showDialog(msg)
    }

    override fun disimissDialog() {
        notifyData?.disconnect()
    }

    override fun getHolderActivity(): FragmentActivity? =
        (lifecycle as? FragmentActivity) ?: (lifecycle as? Fragment)?.requireActivity()

    override fun getClient(): IClient = this


    override fun getHolderContext(): Context? = getHolderActivity()

    override fun <T : Any> bindToLifecycle(): ObservableTransformer<T, T>? =
        observableTransformer as? ObservableTransformer<T, T>


    override fun topViewModelProvider(): ViewModelProvider? =
        miHolderViewModelProviderImpl.topViewModelProvider()

    override fun thisViewModelProvider(): ViewModelProvider? =
        miHolderViewModelProviderImpl.thisViewModelProvider()

}
