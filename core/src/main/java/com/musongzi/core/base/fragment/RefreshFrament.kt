package com.musongzi.core.base.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.TypeAdapter
import com.musongzi.core.ExtensionCoreMethod.dataBindingInflate
import com.musongzi.core.base.adapter.TypeSupportAdaper
import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.vm.MszViewModel
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * 此fragment是不共用同一个  ViewModelStoreOwner 里的viewmodel
 *
 * @param M : LViewModel<*, *>
 * @param D : ViewDataBinding
 */
abstract class RefreshFrament<V :MszViewModel<*, *>, D :ViewDataBinding, Item> : MszFragment<V, D>(),
    IRefreshClient<Item>, IRecycleViewClient<Item> {

    override fun setRefresh(b: Boolean) {
        Log.i(TAG, "setRefresh: 1")
        if (b) {
            Log.i(TAG, "setRefresh: 4")
            refreshView()?.autoRefreshAnimationOnly()
        } else {
            Log.i(TAG, "setRefresh: 5")
            refreshView()?.finishRefresh()
        }
        Log.i(TAG, "setRefresh: 6")
    }

//    override fun isNeedTopViewModelProvider(): Boolean {
//        return fa
//    }


//    override fun instanceViewModel(): V? = InjectionHelp.findViewModel(
//        javaClass,
//        getMainViewProvider(),
//        actualTypeArgumentsViewModelIndex()
//    )

    @SuppressLint("NotifyDataSetChanged")
    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        Log.i(TAG, "notifyDataSetChanged: size = "+(recycleView()?.adapter as TypeSupportAdaper<*>).list)
        recycleView()?.adapter?.notifyDataSetChanged()
    }

    override fun notifyDataSetChangedItem(postiont: Int) {
        super.notifyDataSetChangedItem(postiont)
        if (postiont < 0) {
            Log.i(TAG, "notifyDataSetChangedItem: 小于零 position 不刷新")
            return
        }else{
            Log.i(TAG, "notifyDataSetChangedItem: postiont")
        }
        recycleView()?.adapter?.notifyItemChanged(postiont)
    }

    override fun buildViewByData(datas: List<Item>) {
        disimissDialog()
        notifyDataSetChanged()
        if (datas.isNotEmpty()) {
            normalView()?.visibility = View.GONE
            Log.i(TAG, "buildViewByData: normalView().visibility = View.GONE , " + toString())
        } else if (datas.isEmpty()) {
            isShowHelpTip();
            Log.i(TAG, "buildViewByData: normalView().visibility = View.VISIBLE , " + toString())
            normalView()?.visibility = View.VISIBLE
        }
    }

    protected open fun isShowHelpTip() {

    }


    override fun getSource() = getViewModel() as? ISource<Item>

    override fun getPageEngine() = getViewModel() as? IPageEngine<Item>

    override fun refreshView(): SmartRefreshLayout? = null

    override fun emptyView():ViewGroup? = null

    override fun normalView(): View? = null

    override fun recycleView(): RecyclerView? = null

    override fun superDatabindingName() :String = RefreshFrament::class.java.name

}