package com.musongzi.core.base.client

import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.INotifyDataSetChanged


/**
 * 视图层-刷新状态
 */
interface IRefreshStatusChangeClient : IClient, INotifyDataSetChanged {
    fun setRefresh(b: Boolean)
}