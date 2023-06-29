package com.musongzi.core.base.page

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.ExtensionCoreMethod.exceptionRun
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.page.Book
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.itf.page.ITransformData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

object RefreshUtil {


    @JvmStatic
    fun <I, D> initPage(build: Build<I, D>): PageSupport<I, D> {
        return PageSupport(CallBackImpl(build))
    }

    @JvmStatic
    fun <I> initPageAndRecycleView(easy: EasyCall<I>): IPageEngine<I> {
        return initPageAndRecycleView<I, List<I>>(build = {
            observable = easy.holderObservable()
            client = easy.holderRefreshViewClient()
            adapter = easy.hodlerAdapter()
            transformData = easy.holderTransformData()
            layoutManager = easy.holderLayoutManager()
            onDataRefresh = easy.holderOnPageDataChange()
            book = easy.holderBook()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    @JvmStatic
    fun <I, D> initPageAndRecycleView(build: Build<I, D>.() -> Unit): PageSupport<I, D> {
        val b = Build<I, D>()
        build(b)
        val c = b.client
        val adapter = b.adapter;
        c?.recycleView()?.layoutManager = b.layoutManager?.invoke() ?: LinearLayoutManager(
            null,
            LinearLayoutManager.VERTICAL,
            false
        )
        if (b.onDataRefresh == null) {
            b.onDataRefresh = OnPageDataChange { l, a ->
                if (c is IRefreshClient<*>) {
                    c.setRefresh(false)
                    exceptionRun {
                        (c as? IRefreshClient<I>)?.onVisibilityDataJoin(l)
                    }
                } else {
                    c?.recycleView()?.adapter?.notifyDataSetChanged()
                }
            }
        }
        return initPage(b).apply {
            c?.recycleView()?.adapter = adapter?.invoke(this)
        }
    }

//    fun <D> CoCall<D>.asObservable(): Observable<D>? {
//
//
//    }

    @Deprecated("")
    open class Build<I, D> @JvmOverloads constructor(
        var onDataRefresh: OnPageDataChange<I>? = null,
        var transformData: ITransformData<I, D?>? = null,
        var observable: ((Int) -> Observable<D>?)? = null,
        var client: IRefreshViewClient? = null,
        var adapter: ((ISource<I>) -> RecyclerView.Adapter<*>)? = null,
        var layoutManager: (() -> RecyclerView.LayoutManager)? = null,
        var book: Book? = null,
        var life: ILifeObject? = null,
        var handlerState: ((Int?) -> Unit)? = null,
        var observer: Observer<D>? = null,
        var adMessage: IAdMessage<I>? = null
    ) {

//        fun copy(build: Build<I,D>){
//            onDataRefresh = build.onDataRefresh
//            transformData = build.transformData
//            observable = build.observable
//            observer = build.observer
//            client = build.client
//            client =
//        }

    }


    class CallBackImpl<I, D>(build: Build<I, D>? = null) : PageSupport.CallBack<I, D> {
        var onDataRefresh: OnPageDataChange<I>? = null
        var transformData: ITransformData<I, D?>? = null
        var observable: ((Int) -> Observable<D>?)? = null
        var book: Book? = null
        var life: ILifeObject? = null
        var handlerState: ((Int?) -> Unit)? = null
        var observerImpl: Observer<D>? = null
        var adMessageImple: IAdMessage<I>? = null

        init {
            onDataRefresh = build?.onDataRefresh
            transformData = build?.transformData
            observable = build?.observable
            book = build?.book
            life = build?.life
            handlerState = build?.handlerState
            observerImpl = build?.observer
            adMessageImple = build?.adMessage
        }

        override fun pageSize(): Int {
            return book?.pageSize() ?: IPageEngine.PAGE_SIZE
        }

        override fun thisStartPage(): Int {
            return book?.thisStartPage() ?: IPageEngine.START_PAGE_INDEX
        }

        override fun getThisLifecycle(): LifecycleOwner? {
            return life?.getThisLifecycle()
        }

        override fun runOnUiThread(runnable: Runnable) {
            life?.runOnUiThread(runnable)
        }

        override fun getCode(): Int {
            return 0;
        }

        override fun createPostEvent(): Any? = null

        override fun getObserver(): Observer<D>? {
            return observerImpl
        }

        override fun handlerState(integer: Int?) {
            handlerState?.invoke(integer)
        }

        override fun getAdMessage(): IAdMessage<I>? {
            return adMessageImple
        }

        override fun handlerDataChange(t: MutableList<I>?, action: Int) {
            onDataRefresh?.handlerDataChange(t, action)
        }

        override fun getRemoteData(page: Int): Observable<D>? {
            return observable?.invoke(page)
        }

        override fun transformDataToList(entity: D?): List<I>? {
            return transformData?.transformDataToList(entity)
        }
    }


    interface EasyCall<I> {

        fun hodlerAdapter(): ISource<I>.() -> RecyclerView.Adapter<*>

        fun holderObservable(): (Int) -> Observable<List<I>>?

        fun holderRefreshViewClient(): IRefreshViewClient

        @JvmDefault
        fun holderLayoutManager(): (() -> RecyclerView.LayoutManager)? = {
            LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false)
        }

        @JvmDefault
        fun holderTransformData(): ITransformData<I, List<I>?>? = TransformDataImpl()

        @JvmDefault
        fun holderOnPageDataChange(): OnPageDataChange<I>? = null

        @JvmDefault
        fun holderBook():Book? = null

    }


}