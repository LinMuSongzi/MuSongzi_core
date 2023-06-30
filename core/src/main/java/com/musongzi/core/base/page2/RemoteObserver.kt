package com.musongzi.core.base.page2

import android.util.Log
import com.musongzi.core.itf.IClear
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * 一个简单的观察者
 * 用来保存每次请求的页数[RequestObservableBean.page]和数据[data]
 *
 */
internal class RemoteObserver<D> private constructor(
    var bean: RequestObservableBean<D>? = null,
    var pageLoader: PageLoader<*, D>? = null
) : Observer<D>, IClear {

    /**
     * 唯一标识，时间作为注入方式
     * 用来判断请求的前后
     */
    var createTime = System.currentTimeMillis()

    /**
     * 用来取消订阅
     */
    private var mDisposable: Disposable? = null

    companion object {

        const val TAG = "RemoteObserverP"

        /**
         * 唯一的注入方式
         * @param pageLoader 加载引擎
         * @param bean 当前请求的参数数据
         */
        @JvmStatic
        fun <D> instance(pageLoader: PageLoader<*, D>, bean: RequestObservableBean<D>): RemoteObserver<D> {
            return RemoteObserver(bean, pageLoader).apply {
                pageLoader.addRemoteObserver(this)
            }
        }

    }

    override fun onSubscribe(d: Disposable) {
        Log.i(TAG, "onSubscribe: e")
        mDisposable = d
        pageLoader?.nativeOberver?.onSubscribe(d)
        bean?.apply {
            pageLoader?.setStateInfo(StateInfo(id = createTime, page = page, pageSize = pageSize, state = IState2.ON_SUBSCRIBE_OBSERVABLE_STATE))
        }
    }

    override fun onError(e: Throwable) {
        Log.d(TAG, "onError: $e")
        pageLoader?.nativeOberver?.onError(e)
        bean?.apply {
            pageLoader?.setStateInfo(StateInfo(id = createTime, page = page, pageSize = pageSize, state = IState2.ON_ERROR_BY_SUBSCRIBE_STATE))
        }
    }

    override fun onComplete() {
        Log.d(TAG, "onComplete: ")
        pageLoader?.nativeOberver?.onComplete()
        bean?.apply {
            pageLoader?.setStateInfo(StateInfo(id = createTime, page = page, pageSize = pageSize, state = IState2.ON_COMPLETE_BY_SUBSCRIBE_STATE))
        }
    }

    override fun onNext(t: D) {
        Log.d(TAG, "onNext: $t")
//        data = t
        bean?.apply {
            baseData = t
            pageLoader?.nativeOberver?.onNext(this)
        }
    }

    /**
     * 清空数据
     */
    override fun onClearOperate(any:Any?): Boolean {
        /**
         * 如果还没有数据到达，但是因为某中情况需要清空
         * 那么取消订阅
         */
        if (mDisposable?.isDisposed != false) {
            Log.i(TAG, "clearNow: isDisposed go")
            mDisposable?.dispose()
        }

        bean = null
        pageLoader = null
//        data = null
        return true
    }

}