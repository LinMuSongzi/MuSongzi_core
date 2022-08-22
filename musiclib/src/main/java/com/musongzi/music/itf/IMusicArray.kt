package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IHolderRead
import com.musongzi.core.itf.page.ILimitOnLoaderState
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead2
import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28
 *
 * 一组音乐队列/专辑的集合管理 有两个基本实现类；
 * 核心的实现类[com.musongzi.music.impl.MusicArrayImpl]。
 * 包裹类[com.musongzi.music.impl.MusicContainerArray]
 *
 * 拥有当前播放的音乐下标[IMusicArray.thisPlayIndex]
 * 拥有当前的播放集合[IMusicArray.realData]
 * 一个队列默认持有一个控制器[IPlayController] 控制器的播放音乐仅仅适用于当前集合
 *
 * 一个队列管理其中包含了数据的加载和分页[IPageEngine]的是此队列的remote的数据加载器
 *
 * */
interface IMusicArray<I : IAttribute> : IAttributeArray<I>, IHolderPlayController, IHolderRead<IRead2>{

    /**
     * 当前的播放的基于当前音乐集合的下标
     */
    fun thisPlayIndex(): Int

    /**
     * 切换音乐集合的播放下标，会触发音乐播放
     */
    fun changeThisPlayIndex(index: Int)

    /**
     * 新增一个播放地址，并且添加到当前音乐队列
     *  @param stringUrl 注意：如果当前此播放器的地址与队列的音乐信息某个id相同时或者则不会去扩展音乐队列
     */
    fun changeThisPlayIndexAndAdd(stringUrl: String)

    /**
     * 省略
     */
    override fun enableRefreshLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableRefreshLimit(enable)
    }
    /**
     * 省略
     */
    override fun enableMoreLoadLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableMoreLoadLimit(enable)
    }
    /**
     * 获取当前的音乐队列/专辑的远端数据引擎
     */
    override fun getHolderPageEngine(): IPageEngine<I>

    companion object {
        const val INDEX_NORMAL = 1.shl(28)
        const val INDEX_MASK = INDEX_NORMAL



    }

}