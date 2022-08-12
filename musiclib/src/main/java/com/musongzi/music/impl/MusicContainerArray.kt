package com.musongzi.music.impl

import android.util.Log
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IViewInstance
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead2
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.RemoteDataPacket

/*** created by linhui * on 2022/8/11
 *
 * 一个简单的包裹类 ，是[IMusicArray] 的实现类
 * 仅仅只是静态代理用来埋点
 *
 * */
internal class MusicContainerArray<I:IMediaPlayInfo,D>(
    private val name: String,
    packet: RemoteDataPacket<I, D>, private val trace:IMusicArray<I>? = null
) : BaseMapBusiness<IViewInstance>(), IMusicArray<I> {

    val impl:IMusicArray<I> = MusicArrayImpl(name,packet)

    override fun afterHandlerBusiness() {
        Log.i(TAG, "afterHandlerBusiness: ")
    }

    override fun thisPlayIndex(): Int {
        trace?.thisPlayIndex()
        return impl.thisPlayIndex()
    }

    override fun changeThisPlayIndex(index: Int) {
        trace?.changeThisPlayIndex(index)
        impl.changeThisPlayIndex(index)
    }

    override fun changeThisPlayIndexAndAdd(stringUrl: String) {
        trace?.changeThisPlayIndexAndAdd(stringUrl)
        impl.changeThisPlayIndexAndAdd(stringUrl)
    }

    override fun getHolderPageEngine(): IPageEngine<I> {
        trace?.getPlayController();
        return impl.getHolderPageEngine()
    }

    override fun contains(att: IAttribute, find: ((IAttribute) -> Boolean)?): Boolean {
        trace?.contains(att, null)
        return impl.contains(att, find)
    }

    override fun getAttributeId(): String {
        trace?.attributeId
        return impl.attributeId
    }

    override fun realData(): List<I> {
        trace?.realData()
        return impl.realData()
    }

    override fun getPlayController(): IPlayController? {
        trace?.getPlayController()
        return impl.getPlayController()
    }

    override fun getHolderRead(): IRead2 {
        return impl.getHolderRead()
    }


}