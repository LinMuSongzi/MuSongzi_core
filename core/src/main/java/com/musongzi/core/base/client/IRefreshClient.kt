package com.musongzi.core.base.client

import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderLifecycle


/**
 * 视图层-刷新数据到ui
 * @param <Enttiy>
</Enttiy> */
interface IRefreshClient<Enttiy> : IRefreshStatusChangeClient, IWant {
    fun buildViewByData(datas: List<Enttiy>)
}