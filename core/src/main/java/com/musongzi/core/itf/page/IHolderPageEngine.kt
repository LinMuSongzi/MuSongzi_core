package com.musongzi.core.itf.page

import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.itf.holder.IHolderActivity

@Deprecated("已过期")
interface IHolderPageEngine<I> : ILimitOnLoaderState {

    val source: ISource<I>?

    fun getHolderPageEngine(): IPageEngine<I>?

    fun initAll(client: IRecycleViewClient<I>, coLifeCycle: IHolderActivity, isRefresh: Boolean = false, injection:((target:String)->Any)?)


    override fun enableRefreshLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableRefreshLimit(enable)
    }

    override fun enableMoreLoadLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableMoreLoadLimit(enable)
    }

}