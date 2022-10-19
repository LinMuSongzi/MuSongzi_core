package com.musongzi.core.base.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.databinding.FragmentRecycleListBinding
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.util.StringUtil
import io.reactivex.rxjava3.core.Observable

/**
 * 一个基于集合的基本碎片
 * 核心作用作为view层提供刷新和绑定view功能
 * 快速构建一个几何数据级
 */
open class CollectionsViewFragment : BaseCollectionsViewFragment<FragmentRecycleListBinding, Any, Any>() {

    override fun getCollectionsViewEngine(): IHolderCollections? = null

    override fun getPageEngine(): IPageEngine<Any>? {
        return if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
            getViewModel().getHolderBusiness().base as? IPageEngine<Any>?
        }else{
            null
        }
    }

    override fun isShowHelpTip() {
        val b = getViewModel().getHolderBusiness().base
        if (b.page() == b.thisStartPage() && !getViewModel().getHolderBusiness().isOtherEmptyRes) {
            dataBinding.layoutEmptyDataShow.idMainContentTv.text = getViewModel().collectionsInfo.title
        }
    }


    override fun updateTitle(aNull: String) {
        if (StringUtil.isNull(aNull)) {
            dataBinding.top.root.visibility = View.GONE
        } else {
            dataBinding.top.root.visibility = View.VISIBLE
            dataBinding.top.tvTitle.text = aNull
        }

    }

    override fun createRecycleViewClient(): IRefreshViewClient {
        return object : IRefreshViewClient {
            override fun normalView() = dataBinding.layoutEmptyDataShow.root

            override fun recycleView() = dataBinding.idRecyclerView.idRecyclerView2

            override fun refreshView() = dataBinding.idRecyclerView.idSmartRefreshLayout

            override fun emptyView() = dataBinding.layoutEmptyDataShow.llEmpty

        }
    }

    override fun transformDataToList(entity: Any): List<BaseChooseBean> {
        return ArrayList()
    }

    override fun <I> getRefreshClient(): IRefreshClient<I> {
       return this as IRefreshClient<I>
    }

    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    override fun getRemoteData(index: Int): Observable<Any>?  = null


}