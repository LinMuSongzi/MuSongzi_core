package com.musongzi.core.itf.page

import androidx.lifecycle.Observer

/**
 * 书本数据加载的一个基本引擎接口
 * 具有从新加载，分页，最后页数，总阅览（realData）数据等业务
 */
interface IPageEngine<Item> : Book, ISource<Item>, IRead {
    fun state(): Int
    fun loadState(): Int
    fun page(): Int
    fun lastSize(): Int

    /**
     * page id的
     * 如果刷新，那么此id就会更新。他的含义就是此次刷新数据组的id，下拉不会改变此数值
     *
     * @param observer Observer<Int>
     */
    fun registerPageIdObserver(observer: Observer<Int>)

    companion object {
        const val NONE: Int = -1
        const val STATE_END_REFRASH = 991
        const val STATE_START_NEXT = 996
        const val STATE_START_REFRASH = 998
        const val STATE_END_ERROR = 997
        const val STATE_END_NEXT = 992
        const val NO_MORE_PAGE = 993
        const val PAGE_SIZE = 20
    }
}