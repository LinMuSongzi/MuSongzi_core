package com.mszsupport.itf.page

import com.musongzi.core.itf.page.IDataObservable

/**
 * 数据加载接口
 * @param Entity
 */
interface IDataEngine<D> : Book, IDataObservable<D> {
}