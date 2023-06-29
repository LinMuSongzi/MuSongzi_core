package com.musongzi.core.itf.page

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.musongzi.core.itf.holder.IHolderCheckDataEnd
import com.musongzi.core.base.page2.StateInfo
import com.musongzi.core.itf.IClear

/**
 * 书本数据加载的一个基本引擎接口
 * 具有从新加载，分页，最后页数，总阅览（realData）数据等业务
 */
interface IPageEngine<I> : Book, ISource<I>, IRead, IClear, OnDataChangeListenerOwner<I>, IHolderCheckDataEnd {

    @Deprecated("过期")
    fun observerState(lifecycleOwner: LifecycleOwner,observer: Observer<Int>)

    fun observerStateInfo(lifecycleOwner: LifecycleOwner,observer: Observer<StateInfo>)

    fun state(): Int
    fun loadState(): Int
    fun page(): Int
    fun lastSize(): Int

    fun setMaxCount(maxCount:Int)

//    @Deprecated("")
//    fun getHolderDataChangeListeners():IHolderOnDataChangeListener<I>

    /**
     * page id的
     * 如果刷新，那么此id就会更新。他的含义就是此次刷新数据组的id，下拉不会改变此数值
     * 未完成~
     * @param observer Observer<Int>
     */
    @Deprecated("未完成")
    fun registerPageIdObserver(observer: Observer<Int>)

    companion object {
        const val START_PAGE_INDEX = 0;
        const val NONE: Int = -1
        const val STATE_END_REFRASH = 991
        const val STATE_START_NEXT = 992
        const val STATE_START_REFRASH = 993
        const val STATE_CLEAR = 900
        const val STATE_END_ERROR = 994
        const val STATE_END_NEXT = 995
        const val NO_MORE_PAGE = 996
        const val NO_MORE_BY_LOADED_SUCCED_PAGE = 997
        const val LOADED_SUCCED_PAGE = 980
        const val PAGE_SIZE = 20


        const val CONVERT_POSTION = 989

    }

}