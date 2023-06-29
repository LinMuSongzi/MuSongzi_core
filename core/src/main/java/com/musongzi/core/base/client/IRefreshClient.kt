package com.musongzi.core.base.client

import com.musongzi.core.itf.IWant


/**
 * 视图层-刷新数据到ui
 * @param <Enttiy>
</Enttiy> */
interface IRefreshClient<E> : IRefreshStatusChangeClient, IWant {
    fun onVisibilityDataJoin(datas: List<E>)
}