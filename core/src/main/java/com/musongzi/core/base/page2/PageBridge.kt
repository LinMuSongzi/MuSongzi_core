package com.musongzi.core.base.page2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView.*
import com.cosleep.commonlib.bean.ListRespone
import com.cosleep.commonlib.service.CoCall
import com.heart.core.ExtensionCoreMethod.adapter
import com.heart.core.ExtensionCoreMethod.asListResponeObservable
import com.heart.core.ExtensionCoreMethod.linearLayoutManager
import com.heart.core.base.client.IQuickPageLoaderClient
import com.heart.core.databinding.LayoutEmptyDataNormalBinding
import com.heart.core.itf.page.*
import com.heart.core.util.viewVisibility
import com.musongzi.core.ExtensionCoreMethod.refreshLayoutInit
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IPageBridge
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IPageEngine2
import com.musongzi.core.itf.page.IRead
import com.musongzi.core.itf.page.ISource
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import io.reactivex.rxjava3.core.Observable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 一组业务的刷新组合协调类
 * 1，[pageLoader] 刷新和加载更多，分页和list数据组装
 * 2，[pageCallBack] 是 [pageLoader]的回调
 * 3，[defaultViewSupport] 缺省业务类
 */
class PageBridge<I> private constructor(var call: SimplePageCall<I>, var client: IRefreshViewClient? = null) : IPageBridge<I>, IRead {
    private var pageLoader: IPageEngine2<I, List<I>>
    private var pageCallBack: PageCallBack<I, List<I>>
    private var defaultViewSupport: IDefaultView? = null
    override fun setStartPage(page: Int) {
        call.startPage = page
    }

    override fun setPageSize(pageSize: Int) {
        call.pageSize = pageSize
    }

    override fun getHolderPageEngine(): IPageEngine2<I, List<I>> = pageLoader
    override fun getDefaultSupport(): IDefaultView? {
        return defaultViewSupport
    }

    /**
     * 设置刷新模式
     */
    override fun setRefreshState(isRefresh: Boolean, isLoadMore: Boolean, mRefreshHeader: RefreshHeader?, mRefreshFooter: RefreshFooter?) {
        client?.refreshView()?.apply {
            refreshLayoutInit(
                p = pageLoader,
                isEnableRefresh = isRefresh,
                isEnableLoadMore = isLoadMore,
                mRefreshHeader = mRefreshHeader,
                mRefreshFooter = mRefreshFooter
            )
        }
    }

    /**
     * 构建缺省业务
     * @param view 是activity/fragment，或者实现了[IHolderViewModelProvider]与[IHolderLifecycle]接口的某个类
     * @param dataBinding 缺省图的databinding
     * @param layoutInflater 跟主题样式有关系的用于view/databinding
     */
    override fun <T> buildDefaultSupport(
        view: T,
        dataBinding: LayoutEmptyDataNormalBinding?,
        layoutInflater: LayoutInflater?
    ) where T : IHolderViewModelProvider, T : IHolderLifecycle {
        if (defaultViewSupport == null && dataBinding != null) {
            Log.d(TAG, "buildDefaultSupport: ")
            defaultViewSupport = DefaultViewSupport.createInstance(this, view, dataBinding, layoutInflater)
        }
    }

    override fun refreshShowAnim() {
        client?.refreshView()?.autoRefresh()
    }

    override fun refresh() {
        pageLoader.refresh()
    }

    override fun next() {
        pageLoader.next()
    }

    /**
     * 本地数据源数据
     */
    override fun realData(): List<I> {
        return pageLoader.realData()
    }

    init {
        pageCallBack = object : PageCallBack<I, List<I>> {
            override fun getAdMessage(): IAdMessage<I>? = call.getAdMessage()

            override val thisLifecycle: LifecycleOwner? = call.thisLifecycle

            override fun handlerState(state: Int?) {
                when (state) {
                    IPageEngine.STATE_END_REFRASH -> {
                        client?.refreshView()?.finishRefresh()
                    }
                    IPageEngine.STATE_END_NEXT -> {
                        client?.refreshView()?.finishLoadMore()
                    }
                    IPageEngine.STATE_END_ERROR -> {
                        checkDefautViewVisibility(realData().size)
                    }
                    IPageEngine.NO_MORE_BY_LOADED_SUCCED_PAGE -> {
                        client?.refreshView()?.finishLoadMoreWithNoMoreData()
                    }
                    IPageEngine.LOADED_SUCCED_PAGE -> {
                        client?.refreshView()?.setNoMoreData(false);
                    }
                }
                call.handlerState(state)
            }

            override fun getBusinessMode(): Int = call.getBusinessMode()

            override fun getRemoteData(p: Int, pageSize: Int): Observable<List<I>>? = call.getRemoteData(p, pageSize)

            override fun thisStartPage(): Int = call.thisStartPage()

            override fun pageSize(): Int = call.pageSize()

            override fun convertListByNewData(data: MutableList<I>, transList: MutableList<I>) {
                call.convertListByNewData(data, transList)
            }

            override fun transformDataToList(entity: List<I>?): MutableList<I> = call.transformDataToList(entity)

            override fun createPostEvent(request: RequestObservableBean<List<I>>): Any? = call.createPostEvent(request)


            override fun handlerDataChange(data: MutableList<I>, request: RequestObservableBean<List<I>>) {
                call.handlerDataChange(data, request)
                checkDefautViewVisibility(data.size)
            }

        }
        pageLoader = PageLoader.createInstance(pageCallBack)
//        pageLoader =

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkDefautViewVisibility(size: Int) {
        defaultViewSupport?.apply {
            viewVisibility(normalView(), size <= 0)
            changeDefualtState(defaultState)
//            viewVisibility(emptyDataBinding?.idContentLayout, size <= 0)
        }
        client?.recycleView()?.adapter?.notifyDataSetChanged()
    }

    fun checkAutoDefautViewVisibility() {
        checkDefautViewVisibility(realData().size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataSetChangedOnly(action: Int) {
        if (action >= 0) {
            client?.recycleView()?.adapter?.notifyDataSetChanged()
        } else {
            checkAutoDefautViewVisibility()
        }
    }


    companion object {

        const val PAGE_BRIDGE_KEY = "PAGE_BRIDGE_KEY"

        const val TAG = "PageBridge"


        /**
         * 最基本的使用方式
         * @param lm recycleview的layoutmanager
         * @param remote 远端的数据源被观察者，被刷新或者加载更多触发订阅。会传递一个page页码
         * @param source 当前的本地数据源的对象，用来构造[RecycleView.Adapter]
         *
         * @return 返回[IPageBridge] 给与使用者。
         */
        fun <I> IQuickPageLoaderClient.createPageLoader(
            lm: LayoutManager,
            remote: (page: Int, pageSize: Int) -> CoCall<ListRespone<I>?>?,
            source: ISource<I>.() -> Adapter<*>
        ): IPageBridge<I> {
            val pb = PageBridge(call = object : SimplePageCall<I>(getThisLifecycle()) {
                override fun getRemoteData(page: Int, pageSize: Int): Observable<List<I>>? {
                    return remote.invoke(page, pageSize)?.asListResponeObservable(holderCoLifeCycle)
                }
            }, client = this)
//            pb.source =  SourceImpl(pb.getHolderPageEngine().realData())
            recycleView()?.apply {
                layoutManager = lm
                adapter = source.invoke(pb)
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }


        /**
         * 创建一个pageloader
         * @param page [SimplePageCall] 回调函数 [SimplePageCall.pageSize]
         */
        fun <I, B : ViewDataBinding> IQuickPageLoaderClient.createPageLoader(
            page: SimplePageCall<I>,
            isSetBean: Boolean = true,
            lm: LayoutManager,
            bClazz: Class<B>,
            create: ((db: B, type: Int) -> Unit)? = null,
            convert: ((db: B, I, position: Int) -> Unit)? = null,
        ): IPageBridge<I> {
            val pb = PageBridge(call = page, client = this)
//            pb.source = SourceImpl(pb.getHolderPageEngine().realData())
            recycleView()?.apply {
                layoutManager = lm
                adapter = pb.adapter(bClazz, create ?: { _, _ -> }, convert ?: { _, _, _ -> }, isSetBean)
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }

        /**
         * 创建一个pageloader
         * @param page [SimplePageCall] 回调函数 [SimplePageCall.pageSize]
         */
        fun <I> IQuickPageLoaderClient.createPageLoader(
            page: SimplePageCall<I>,
            lm: LayoutManager,
            myAdapter: ISource<I>.() -> Adapter<*>,
        ): IPageBridge<I> {
            val pb = PageBridge(call = page, client = this)
            recycleView()?.apply {
                layoutManager = lm
                adapter = myAdapter.invoke(pb)
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }

        fun <I, B : ViewDataBinding> IQuickPageLoaderClient.createPageLoaderByLinearLayoutManager(
            page: SimplePageCall<I>,
            isSetBean: Boolean = true,
            bClazz: Class<B>,
            create: ((db: B, type: Int) -> Unit)? = null,
            convert: ((db: B, I, position: Int) -> Unit)? = null,
        ): IPageBridge<I> {
            val pb = PageBridge(call = page, client = this)
//            pb.source = SourceImpl(pb.getHolderPageEngine().realData())
            recycleView()?.apply {
                linearLayoutManager {
                    pb.adapter(bClazz, create ?: { _, _ -> }, convert ?: { _, _, _ -> }, isSetBean)
                }
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }

        fun <I> IQuickPageLoaderClient.createPageLoaderByLinearLayoutManager(
            page: SimplePageCall<I>,
            source: ISource<I>.() -> Adapter<*>
        ): IPageBridge<I> {
            val pb = PageBridge(call = page, client = this)
//            pb.source = SourceImpl(pb.getHolderPageEngine().realData())
            recycleView()?.apply {
                linearLayoutManager {
                    source.invoke(pb)
                }
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }

        fun <I> IQuickPageLoaderClient.createPageLoaderByLinearLayoutManager(
            remote: (page: Int, pageSize: Int) -> CoCall<ListRespone<I>?>?,
            source: ISource<I>.() -> Adapter<*>
        ): IPageBridge<I> {
            val pb = PageBridge(call = object : SimplePageCall<I>(getThisLifecycle()) {
                override fun getRemoteData(page: Int, pageSize: Int): Observable<List<I>>? {
                    return remote.invoke(page, pageSize)?.asListResponeObservable(holderCoLifeCycle)
                }
            }, client = this)
//            pb.source =  SourceImpl(pb.getHolderPageEngine().realData())
            recycleView()?.apply {
                linearLayoutManager {
                    source.invoke(pb)
                }
            }
            initQuickPageLoaderClient(this, pb)
            return pb
        }

        private fun initQuickPageLoaderClient(client: IQuickPageLoaderClient, pb: PageBridge<*>) {
            pb.setRefreshState(
                isRefresh = client.isRefresh(),
                isLoadMore = client.isLoadMore(),
                mRefreshHeader = client.getHolderRefreshHeader(),
                mRefreshFooter = client.getHolderRefreshFooter()
            )
            pb.buildDefaultSupport(view = client, dataBinding = client.getDefualtDataBinding(), layoutInflater = client.getLayoutInflater())
        }


        /**
         * 这种方式的注入
         * 只使用于kotlin的属性里
         */
        fun <I> newPageLoader(
            remote: (page: Int, pageSize: Int) -> CoCall<ListRespone<I>?>?,
            source: ISource<I>.() -> Adapter<*>,
            layoutManager: LayoutManager,
            onPageLoadInit: SimplePageCall<I>.() -> Unit
        ): ReadOnlyProperty<IQuickPageLoaderClient, IPageBridge<I>?> {

            return object : ReadOnlyProperty<IQuickPageLoaderClient, IPageBridge<I>?> {

                var v: IPageBridge<I>? = null

                override fun getValue(
                    thisRef: IQuickPageLoaderClient,
                    property: KProperty<*>
                ): IPageBridge<I>? {
                    val li = thisRef.getThisLifecycle()?.lifecycle
                    Log.i(TAG, "newPageLoader getValue1: ${li?.currentState}")
                    if (li == null) {
                        return null
                    } else if (!li.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                        Log.i(TAG, "newPageLoader getValue2: ${li.currentState}")
                        return null
                    }
                    if (v == null) {
                        li.addObserver(object : DefaultLifecycleObserver {
                            override fun onCreate(owner: LifecycleOwner) {
                                v = thisRef.createPageLoader(lm = layoutManager, { p, s ->
                                    remote.invoke(p, s)
                                }, source)
                                onPageLoadInit((v as PageBridge<I>).call)
                                owner.lifecycle.removeObserver(this)
                            }
                        })

                    }
                    return v
                }

            }


        }
    }


}