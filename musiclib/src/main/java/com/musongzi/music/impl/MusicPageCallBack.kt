package com.musongzi.music.impl

import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.base.page.AbstractCallBack
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.music.itf.RemoteDataPacket
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/8/3 */
class MusicPageCallBack<I : IAttribute, D>(var packet: RemoteDataPacket<I, D>) : AbstractCallBack<I, D>() {

    override fun pageSize() = IPageEngine.PAGE_SIZE

    override fun thisStartPage() = 0

    override fun getRemoteData(page: Int): Observable<D>? {
       return packet.getRemoteData(page)
    }

    override fun getThisLifecycle(): LifecycleOwner? {
        return null;
    }

    override fun runOnUiThread(runnable: Runnable) {

    }

    override fun handlerData(t: MutableList<I>?, action: Int) {
        packet.handlerData(t,action)
    }

    override fun transformDataToList(entity: D): List<I> {
        return packet.transformDataToList(entity)
    }

    override fun getCode(): Int {
        return 0;
    }


    override fun handlerState(integer: Int?) {
//        impl.han
    }

}