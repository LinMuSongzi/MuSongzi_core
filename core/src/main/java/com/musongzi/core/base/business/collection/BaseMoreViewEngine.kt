package com.musongzi.core.base.business.collection

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.ExtensionCoreMethod.wantPick
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IDataEngine
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.base.page.PageSupport
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.UiUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 数据加载，具备分页刷新功能
 * @param Item 数据项类型
 * @param Data 总数据体（后端返回的）
 * @property dataPageSupport PageSupport<Item, Data> 数据分页引擎类
 * @property callBack IRefreshViewModel<Item> 回调模型
 * @property instanceAdapter Adapter<*> 子类需要实现的adapter，
创建方式由[ISource]的扩展函数
[ExtensionMethod.adapter]实例化 ，可以根据业务实现多种type
 * @property observer Observer<Data> 当前总体数据的一个观察者回调
 * @property initFlag Boolean 是否初始化
 */
abstract class BaseMoreViewEngine<Item, Data> : ICollectionsViewEngine<Item>,
    PageSupport.CallBack<Item, Data>, IHolderContext, IAnalyticSpanner<List<Item>, Data> {
    /**
     * 分页引擎
     */
    private lateinit var dataPageSupport: PageSupport<Item, Data>

    /**
     * 一个抽象的View层。它的实现类在目前框架中是一个[CollectionsViewModel]
     */
    private lateinit var callBack: IRefreshViewModel<Item>
    private lateinit var instanceAdapter: RecyclerView.Adapter<*>
    private var supportDataEngine: IDataEngine<Data>? = null
    private var observer: Observer<Data>? = null
    private var initFlag = false
    private var localSavedStateHandle: ISaveStateHandle? = null
//    private lateinit var  datas :List<>


    override fun runOnUiThread(runnable: Runnable) {
        UiUtil.post(runnable)
    }

    public var TAG = javaClass.simpleName
    final override fun getAdapter(): RecyclerView.Adapter<*> = instanceAdapter

    override fun bindAdapter() {
        instanceAdapter = myAdapter()
    }

    final override fun init(i: IRefreshViewModel<*>) {
        if (!initFlag) {
            onInitBefore(i);
            this.callBack = i as IRefreshViewModel<Item>
            dataPageSupport = PageSupport(this)

            dataPageSupport.enableRefreshLimit(enableLoaderLimite())
            initFlag = true
            i.getBundle()?.getBundle(CollecttionsEngine.B)?.let {
                runOnHadBundleData(it)
            }
            onInitAfter(i);
        }
    }

    final override fun init(
        i: IRefreshViewModel<*>,
        run: (IPageEngine<*>) -> RecyclerView.Adapter<*>
    ) {
        if (!initFlag) {
            onInitBefore(i);
            this.callBack = i as IRefreshViewModel<Item>
            dataPageSupport = PageSupport(this)
            dataPageSupport.enableRefreshLimit(enableLoaderLimite())
            instanceAdapter = run(dataPageSupport)
            initFlag = true
            i.getBundle()?.getBundle(CollecttionsEngine.B)?.let {
                runOnHadBundleData(it)
            }
            onInitAfter(i);
        }
    }

    protected open fun onInitAfter(iRefreshViewModel: IRefreshViewModel<Item>) {
    }

    /**
     * 慎重重写
     */
    protected open fun onInitBefore(i: IRefreshViewModel<*>) {

    }

    protected open fun enableLoaderLimite() = true

    protected open fun runOnHadBundleData(it: Bundle) {

    }

    protected open fun changeRemoteDataEngin(data: IDataEngine<Data>?) {
        this.supportDataEngine = data
    }

    protected open fun changeObserver(observer: Observer<Data>?) {
        this.observer = observer
    }

    override fun getHolderContext(): Context? = callBack.getHolderContext()

    override fun getLayoutManger(): RecyclerView.LayoutManager? = null

    override fun getCode(): Int = 0

    override fun getObserver(): Observer<Data>? = observer

    override fun refresh() {
        dataPageSupport.refresh()
    }

    override fun next() {
        dataPageSupport.next()
    }

    override fun state(): Int {
        return dataPageSupport.state()
    }

    override fun loadState(): Int = state()
    override fun page(): Int = dataPageSupport.page()
    override fun lastSize(): Int = dataPageSupport.lastSize()
    override fun realData(): List<Item> = dataPageSupport.realData()
    override fun pageSize(): Int = supportDataEngine?.pageSize() ?: IPageEngine.PAGE_SIZE

    /**
     * 这里是代表开始页数，具体你要看自己的接口
     * @return Int
     */
    override fun thisStartPage(): Int = supportDataEngine?.thisStartPage() ?: 1

    override fun createPostEvent(): Nothing? = null

    override fun handlerState(integer: Int?) {}

    override fun handlerData(items: List<Item>, action: Int) {
        callBack.getRefreshClient()?.buildViewByData(items)
    }

    final override fun getRemoteData(page: Int) =
        supportDataEngine?.getRemoteData(page) ?: getRemoteDataReal(page)

    protected abstract fun getRemoteDataReal(page: Int): Observable<Data>?

    override fun onEmptyViewCreate(v: View?) {

    }

    override fun onRefreshViewClientOnEvent(i: IRefreshViewClient) {
    }

    abstract fun myAdapter(): RecyclerView.Adapter<*>

    override fun registerPageIdObserver(observer: androidx.lifecycle.Observer<Int>) {
        dataPageSupport.registerPageIdObserver(observer)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage() {

    }

    override fun getPageSupport() = this

    override fun getAdMessage(): IAdMessage<Item>? = null

    override fun getRefreshViewModel(): IRefreshViewModel<Item> = callBack

    override fun getTag(): String = javaClass.name

    fun getMainLifecycle(): IHolderLifecycle? = callBack.getRefreshClient()?.getMainLifecycle()

    override fun getThisLifecycle(): LifecycleOwner? =
        callBack.getRefreshClient()?.getThisLifecycle()

    fun <C : IChoose> pickSingle(pick: C) {
        (callBack as? IHandlerChooseViewModel<*>)?.wantPick()?.pickRun(pick)
    }

    fun <C : IChoose> pickSingle(view: View, pick: C) {
        view.setOnClickListener {
            (callBack as? IHandlerChooseViewModel<*>)?.wantPick()?.pickRun(pick)
        }
    }

    override fun useSpanner(data: Data): List<Item>? {
        return null
    }


    override fun getHolderSavedStateHandle(): ISaveStateHandle {
        if (localSavedStateHandle == null) {
            localSavedStateHandle = LocalSavedHandler()
        }
        return localSavedStateHandle!!
    }

    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        localSavedStateHandle = savedStateHandle
    }
}