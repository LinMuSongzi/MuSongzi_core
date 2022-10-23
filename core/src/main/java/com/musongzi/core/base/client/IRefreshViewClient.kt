package com.musongzi.core.base.client

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout

interface IRefreshViewClient {

    fun normalView(): View? = null

    fun recycleView(): RecyclerView?

    fun refreshView(): SmartRefreshLayout?

    fun emptyView():ViewGroup? = null

}