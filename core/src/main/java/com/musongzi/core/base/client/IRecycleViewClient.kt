package com.musongzi.core.base.client

import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource


interface IRecycleViewClient<I> : IRefreshViewClient{

    fun getPageEngine(): IPageEngine<I>?

    fun getSource(): ISource<I>?

}