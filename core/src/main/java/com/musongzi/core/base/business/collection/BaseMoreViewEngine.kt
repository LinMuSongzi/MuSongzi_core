package com.musongzi.core.base.business.collection

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IDataEngine
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.PageSupport
import com.musongzi.test.simple.MszObserver
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
创建方式由[com.android.playmusic.l.business.itf.ISource]的扩展函数
[com.android.playmusic.l.common.ExtensionMethod.adapter]实例化 ，可以根据业务实现多种type
 * @property observer Observer<Data> 当前总体数据的一个观察者回调
 * @property initFlag Boolean 是否初始化
 */
abstract class BaseMoreViewEngine<Item, Data> : INewCollectionsViewEngine<Item>,
    PageSupport.CallBack<Item, Data>, IHolderContext {
    /**
     * 分页引擎
     */
    private lateinit var dataPageSupport: PageSupport<Item, Data>

    /**
     * 一个抽象的View层。它的实现类在目前框架中是一个[com.android.playmusic.l.viewmodel.imp.CollectionsViewModel]
     */
    protected lateinit var callBack: IRefreshViewModel<Item>
    private lateinit var instanceAdapter: RecyclerView.Adapter<*>
    var supportDataEngine: IDataEngine<Data>? = null
    private val observer: Observer<Data> = createObserver()
    private var initFlag = false

    final override fun getAdapter(): RecyclerView.Adapter<*> = instanceAdapter

    final override fun init(callBack: IRefreshViewModel<*>) {
        if (!initFlag) {
            this.callBack = callBack as IRefreshViewModel<Item>
            dataPageSupport = PageSupport(this)
            instanceAdapter = myAdapter()
            initFlag = true
//            Log.i(TAG, "init: $callBack")
            laterInit(callBack.getBundle())
        }
    }

    protected open fun laterInit(bundle: Bundle?) {
//        Log.i(TAG, "laterInit: ")
    }

    protected open fun createObserver(): Observer<Data> = MszObserver()

    override fun getHolderContext(): Context? = callBack.getHolderContext()

    override fun getLayoutManger(): RecyclerView.LayoutManager? = null

    override fun getCode(): Int = 0

    override fun getObserver(): Observer<Data> = observer

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
    override fun pageSize(): Int = IPageEngine.PAGE_SIZE

    /**
     * 这里是代表开始页数，具体你要看自己的接口
     * @return Int
     */
    override fun thisStartPage(): Int = 1

    override fun createPostEvent(): Nothing? = null

    override fun handlerState(integer: Int) {}

    override fun handlerData(items: List<Item>, action: Int) {
//        if (callBack != null) {
        callBack.buildViewByData(items)
//        }
    }

    final override fun getRemoteData(page: Int) =
        supportDataEngine?.getRemoteData(page) ?: getRemoteDataReal(page)

    protected abstract fun getRemoteDataReal(index: Int): Observable<Data>?

//    override fun lifecycleOwner() = callBack.lifecycleOwner()

    override fun onEmptyViewCreate(v: View?) {

    }

//    override fun getApiInstance(): Api? = callBack.getApiInstance()

    override fun onRefreshViewClientEvent(i: IRefreshViewClient) {
//        instanceAdapter = myAdapter();
    }

    abstract fun myAdapter(): RecyclerView.Adapter<*>

    override fun registerPageIdObserver(observer: androidx.lifecycle.Observer<Int>) {
        dataPageSupport.registerPageIdObserver(observer)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage() {

    }

//    /**
//     * 反射默认去data里面的 list ，如果没意外的话~
//     * @param entity Data
//     * @return List<Item>
//     */
//    abstract override fun transformDataToList(entity: Data) :List<Item>

    override fun getPageSupport() = this

    override fun getAdMessage(): IAdMessage<Item>? = null

    fun getRefreshViewModel(): IRefreshViewModel<Item> = callBack

    override fun getTag(): String = javaClass.name


//    companion object {
//        const val TAG = "CollectionsViewEngine"
//
//        @JvmStatic
//        fun <D, I, C> transformData(entity: D, c: Class<C>, className: String, index: Int): List<I> {
//            val clazz = ApkUtil.findGenericClass<D>(c, className, index)
//            var f = clazz.getDeclaredField("data")
//            f.isAccessible = true
//            val data = f.get(entity)
//            f = data.javaClass.getDeclaredField("list")
//            f.isAccessible = true
//            val list = f.get(data)
//            return list as List<I>;
//        }
//
//    }

    override fun getMainLifecycle(): IHolderLifecycle? = callBack.getMainLifecycle()

    override fun getThisLifecycle(): LifecycleOwner? = callBack.getThisLifecycle()
}