package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IHolderRead
import com.musongzi.core.itf.page.ILimitOnLoaderState
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead2
import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
interface IMusicArray<I : IAttribute> : IAttributeArray<I>, IHolderPlayController, IHolderRead<IRead2>{
    fun thisPlayIndex(): Int
    fun changeThisPlayIndex(index: Int)
    fun changeThisPlayIndexAndAdd(stringUrl: String)

    override fun enableRefreshLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableRefreshLimit(enable)
    }

    override fun enableMoreLoadLimit(enable: Boolean) {
        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableMoreLoadLimit(enable)
    }

    override fun getHolderPageEngine(): IPageEngine<I>

    companion object {
        const val INDEX_NORMAL = 1.shl(28)
        const val INDEX_MASK = INDEX_NORMAL



    }

}