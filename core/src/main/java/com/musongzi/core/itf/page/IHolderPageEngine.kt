package com.musongzi.core.itf.page

interface IHolderPageEngine<I> : ILimitOnLoaderState {


    fun getHolderPageEngine(): IPageEngine<I>?

}