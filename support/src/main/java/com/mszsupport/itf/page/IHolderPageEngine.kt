package com.mszsupport.itf.page

interface IHolderPageEngine<I> : ILimitOnLoaderState {


    fun getHolderPageEngine(): IPageEngine<I>?

}