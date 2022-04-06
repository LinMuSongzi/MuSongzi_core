package com.musongzi.core.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.vm.MszViewModel
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.jetbrains.annotations.NotNull
import java.lang.Exception


/**
 * 此fragment是不共用同一个  ViewModelStoreOwner 里的viewmodel
 *
 * @param M : LViewModel<*, *>
 * @param D : ViewDataBinding
 */
abstract class LRefreshFrament<V : @androidx.annotation.NonNull MszViewModel<*, *>, D : @androidx.annotation.NonNull ViewDataBinding, Item> : ModelFragment<V, D>(),
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

//    override fun getCallBack(): Any? = this

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
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


    override fun getSource() = getMainViewModel() as? ISource<Item>

    override fun getPageEngine() = getMainViewModel() as? IPageEngine<Item>

    override fun refreshView(): SmartRefreshLayout? = null

    override fun emptyView():ViewGroup? = null

    override fun normalView(): View? = null

    override fun recycleView(): RecyclerView? = null

    override fun superDatabindingName() :String = LRefreshFrament::class.java.name

}