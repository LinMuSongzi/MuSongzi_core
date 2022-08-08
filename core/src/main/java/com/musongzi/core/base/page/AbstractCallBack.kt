package com.musongzi.core.base.page

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IPageEngine
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

/*** created by linhui * on 2022/8/9 */
abstract class AbstractCallBack<I,D> :PageSupport.CallBack<I,D> {

    private val TAG = javaClass.simpleName

    override fun pageSize(): Int {
       return IPageEngine.PAGE_SIZE
    }

    override fun thisStartPage() = 0

//    override fun getRemoteData(page: Int): Observable<D>? {
//        TODO("Not yet implemented")
//    }

//    override fun handlerData(t: MutableList<I>?, action: Int) {
//        TODO("Not yet implemented")
//    }

//    override fun transformDataToList(entity: D): List<I> {
//        TODO("Not yet implemented")
//    }

//    override fun getThisLifecycle(): LifecycleOwner? {
//        TODO("Not yet implemented")
//    }

    override fun getCode() = 0

    override fun createPostEvent() = null

    override fun getObserver() = null

    override fun handlerState(integer: Int?) {
        Log.i(TAG, "handlerState: $integer")
    }

    override fun getAdMessage() = null
}