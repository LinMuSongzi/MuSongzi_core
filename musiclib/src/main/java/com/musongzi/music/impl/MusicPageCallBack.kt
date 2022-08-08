package com.musongzi.music.impl

import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.base.page.PageSupport
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.music.itf.IMusicArray
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

/*** created by linhui * on 2022/8/3 */
class MusicPageCallBack<I : IAttribute, D>(var proxy: MusicDataProxy<I, D>) : PageSupport.CallBack<I, D> {

    override fun pageSize() = IPageEngine.PAGE_SIZE

    override fun thisStartPage() = 0

    override fun getRemoteData(page: Int): Observable<D>? {
       return proxy.getRemoteData(page)
    }

    override fun getThisLifecycle(): LifecycleOwner? {
        return null;
    }

    override fun handlerData(t: MutableList<I>?, action: Int) {
        proxy.handlerData(t,action)
    }

    override fun transformDataToList(entity: D): List<I> {
        return proxy.transformDataToList(entity)
    }

    override fun getCode(): Int {
        return 0;
    }

    override fun createPostEvent(): Any? {
        return null
    }

    override fun getObserver(): Observer<D>? {
       return null
    }

    override fun handlerState(integer: Int?) {
//        impl.han
    }

    override fun getAdMessage(): IAdMessage<I>? {
        return null
    }
}