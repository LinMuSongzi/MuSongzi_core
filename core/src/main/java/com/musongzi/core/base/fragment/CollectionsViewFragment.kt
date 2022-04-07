package com.musongzi.core.base.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.databinding.FragmentRecycleListBinding
import com.musongzi.core.util.StringUtil

/**
 * 一个基于集合的基本碎片
 * 核心作用作为view层提供刷新和绑定view功能
 */
open class CollectionsViewFragment : BaseCollectionsViewFragment<FragmentRecycleListBinding>(),
    CollectionsViewClient {


    override fun <I, D> getCollectionsViewEngine(): IHolderCollections? = null

    override fun initView() {
        getMainViewModel()!!.getHolderBusiness().handlerView(recycleView(), refreshView())
        getMainViewModel()!!.getHolderBusiness().handlerEmptyRes(emptyView())
    }


    override fun isShowHelpTip() {
        val b = getMainViewModel()!!.getHolderBusiness().base
        if (b.page() == b.thisStartPage() && !getMainViewModel()!!.getHolderBusiness().isOtherEmptyRes) {
            dataBinding.layoutEmptyDataShow.idMainContentTv.text = getMainViewModel()!!.emptyString
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

    override fun getViewModelProvider(thisOrTopProvider: Boolean) = if (thisOrTopProvider) ViewModelProvider(this) else ViewModelProvider(requireActivity());

    override fun createRecycleViewClient(): IRefreshViewClient {
        return object : IRefreshViewClient {
            override fun normalView() = dataBinding.layoutEmptyDataShow.root

            override fun recycleView() = dataBinding.idRecyclerView.idRecyclerView2

            override fun refreshView() = dataBinding.idRecyclerView.idSmartRefreshLayout

            override fun emptyView() = dataBinding.layoutEmptyDataShow.llEmpty

        }
    }

    override fun <DATA, ITEM> transformDataToList(entity: DATA): List<ITEM> {
        TODO("Not yet implemented")
    }


}