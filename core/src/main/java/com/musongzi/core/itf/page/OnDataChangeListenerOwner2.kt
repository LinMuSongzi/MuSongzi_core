package com.musongzi.core.itf.page

import com.musongzi.core.base.page2.RequestObservableBean

interface OnDataChangeListenerOwner2<I, D> {

    fun getHolderDataChangeListeners2(): IHolderOnDataChangeListener2<I, RequestObservableBean<D>>?

}