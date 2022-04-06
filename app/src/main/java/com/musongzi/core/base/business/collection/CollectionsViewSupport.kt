package com.musongzi.core.base.business.collection

import androidx.recyclerview.widget.RecyclerView

interface CollectionsViewSupport {

    fun getLayoutManger(): RecyclerView.LayoutManager?
    fun getAdapter(): RecyclerView.Adapter<*>?

}