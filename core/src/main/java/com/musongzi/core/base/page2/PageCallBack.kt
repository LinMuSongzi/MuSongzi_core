package com.musongzi.core.base.page2

import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.page.IDataObservable2
import com.musongzi.core.itf.page.IAdMessage
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead
import io.reactivex.rxjava3.core.Observable


/**
 * [PageCallBack] 是一组回调函数接口
 * 泛型中 I 代表集合每一项数据类型
 * 泛型中 D 代表获取到的被观察者的数据model
 * 此接口即作为和控制给与 [PageLoader] 设置控制，也是[PageLoader.createInstance]的唯一实例化一个注入类型
 */
interface PageCallBack<I, D> : IDataObservable2<D> {
    /**
     * 广告参数
     */
    fun getAdMessage(): IAdMessage<I>? = null

    /**
     * view 层的 activity/fragment
     */
    val thisLifecycle: LifecycleOwner?

    /**
     * 处理state ,state是一组记录着 数据加载到完毕的一组状态
     * 对应state 在 [com.heart.core.itf.page.IPageEngine.Companion]
     * @param state
     */
    fun handlerState(state: Int?){}

    /**
     * 获取业务处理数据并且的业务模式
     *
     * @return 返回的模式固定类型
     *  [com.heart.core.itf.page.IRead.SIMPLE_MODE] 默认实现（默认处理数据的拼接和刷新清空）
     * [com.heart.core.itf.page.IRead.POSTION_MODE] 自我实现 需要结合[convertListByNewData] 方法是用
     *
     */
    @Deprecated("暂时标记过")
    fun getBusinessMode(): Int = IRead.SIMPLE_MODE

    /**
     * 获取一个数据被观察者
     * @param page 请求的页数，当前计算方法有默认实现,请查阅[convertPage]
     */
    override fun getRemoteData(page: Int, pageSize: Int): Observable<D>?

    /**
     * 处理数据更新
     * @param data 最终的数据集
     * @param request 这次请求的参数 [RequestObservableBean] 里面包含的页数 [RequestObservableBean.page] ,还有本次数据[RequestObservableBean.baseData]
     */
    fun handlerDataChange(data: MutableList<I>, request: RequestObservableBean<D>)

    /**
     * 一个给与 订阅观察者的post的函数
     * 内部是发射一个otto
     * @param request 这次请求的参数
     * @return 返回的是一个默认发送的 otto 对象
     */
    @Deprecated("过期")
    fun createPostEvent(request: RequestObservableBean<D>): Any? = null

    /**
     * 返回首页的index
     * @return 第一页
     */
    fun thisStartPage(): Int = IPageEngine.START_PAGE_INDEX

    /**
     * 数据转换，根据订阅的观察者转换数据为 list
     * @return 返回被转化的数据
     */
    fun transformDataToList(entity: D?): List<I>

    /**
     * 跟[getBusinessMode]配合这使用，自己去定义数据的拼接方式
     * @param data 当前的数据集
     * @param transList 订阅到被观察者后回调的数据集
     */
    fun convertListByNewData(data: MutableList<I>, transList: MutableList<I>){}

    /**
     * 当前一页的数量
     * @return 数量
     */
    fun pageSize(): Int = IPageEngine.PAGE_SIZE

    /**
     * 根据参数返回需要加载的指定页数index
     * 有默认实现
     * @param readyLoadPage 准备加载这一页的index
     * @param dataSize 当前的数据量
     * @param startPage 首页的index
     * @param pageSize 一页的最大数
     *
     * @return 返回的是当前的需要加载页数index
     */
    fun convertPage(readyLoadPage: Int, dataSize: Int, startPage: Int, pageSize: Int): Int {
        if (readyLoadPage == startPage || dataSize == 0) {
            return startPage
        }
        return dataSize / pageSize + startPage
    }
}