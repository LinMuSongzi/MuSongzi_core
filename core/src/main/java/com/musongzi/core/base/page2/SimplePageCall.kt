package com.musongzi.core.base.page2

import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.page.IRead.Companion.SIMPLE_MODE
import com.musongzi.core.itf.page.IAdMessage

/**
 * [PageCallBack] 默认实现累
 * 默认实现的某些简单的回调
 */
abstract class SimplePageCall<I>(var lifecycleOwner: LifecycleOwner?) : PageCallBack<I, List<I>> {

    var pageSize = 20
    var startPage = 0
    var bMode = SIMPLE_MODE


    override val thisLifecycle: LifecycleOwner?
        get() = lifecycleOwner

    override fun transformDataToList(entity: List<I>?): MutableList<I> {
        return (entity as? MutableList<I>) ?: mutableListOf()
    }

    override fun getAdMessage(): IAdMessage<I>? = null

    override fun handlerState(integer: Int?) {

    }

    override fun handlerDataChange(data: MutableList<I>, request: RequestObservableBean<List<I>>) {

    }

    override fun getBusinessMode(): Int = bMode


    override fun thisStartPage(): Int = startPage

    override fun pageSize(): Int = pageSize

    override fun convertListByNewData(data: MutableList<I>, transList: MutableList<I>) {

    }

    override fun createPostEvent(request: RequestObservableBean<List<I>>): Any? = null

}