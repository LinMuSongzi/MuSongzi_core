package com.musongzi.core.itf.page

import android.view.LayoutInflater
import com.musongzi.core.base.page.ILayoutEmptyView
import com.musongzi.core.base.page2.IDefaultView
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader

interface IPageBridge<I> : ILimitOnLoaderState, ISource<I>, IRead {

    fun setStartPage(page:Int)

    fun setPageSize(pageSize:Int)
    fun getHolderPageEngine(): IPageEngine2<I, List<I>>

    override fun enableRefreshLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableRefreshLimit(enable)
    }

    override fun enableMoreLoadLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableMoreLoadLimit(enable)
    }

    fun getDefaultSupport(): IDefaultView?


    fun setRefreshState(isRefresh: Boolean, isLoadMore: Boolean,mRefreshHeader: RefreshHeader? = null, mRefreshFooter: RefreshFooter? = null)
    fun <T> buildDefaultSupport(
        view: T,
        dataBinding: ILayoutEmptyView?,
        layoutInflater: LayoutInflater?
    ) where T : IHolderViewModelProvider, T : IHolderLifecycle

    fun refreshShowAnim()

}