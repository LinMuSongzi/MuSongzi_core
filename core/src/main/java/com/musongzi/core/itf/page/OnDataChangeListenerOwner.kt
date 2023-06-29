package com.musongzi.core.itf.page

@Deprecated("")
interface OnDataChangeListenerOwner<I> {

    @Deprecated("pageloader过期")
    fun getHolderDataChangeListeners(): IHolderOnDataChangeListener<I>?

}