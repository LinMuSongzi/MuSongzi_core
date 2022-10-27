package com.musongzi.core.base.fragment

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.CollectionsViewSupport
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.itf.page.IDataEngine
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.Observable

abstract class BaseCollectionsViewFragment<B : ViewDataBinding, ITEM, DATA> :
    RefreshFrament<CollectionsViewModel, B, ITEM>(), CollectionsViewClient,
    CollectionsViewSupport {

//    var totalLiveData = MutableLiveData(0)

    override fun actualTypeArgumentsViewModelIndex() = 0
    override fun actualTypeArgumentsDatabindinIndex(): Int = 0
    override fun superFragmentName(): String = RefreshFrament::class.java.name
    override fun superDatabindingName(): String = BaseCollectionsViewFragment::class.java.name

    private lateinit var mRecycleViewClient: IRefreshViewClient

    abstract fun createRecycleViewClient(): IRefreshViewClient

    override fun initView() {
        getViewModel().getHolderBusiness().handlerArguments(arguments)
        getViewModel().getHolderBusiness().handlerView(recycleView(), refreshView())
        getViewModel().getHolderBusiness().handlerEmptyRes(emptyView())
    }

    override fun handlerArguments() {
        super.handlerArguments()
        mRecycleViewClient = createRecycleViewClient()
    }

    override fun initEvent() {
        (getViewModel().getHolderBusiness().base as? IHolderCollections)?.onRefreshViewClientOnEvent(
            mRecycleViewClient
        );
    }

    override fun initData() {
        if (getViewModel().collectionsInfo.openLazyLoad.and(CollectionsViewModel.LAZY_LOAD_CLOSE_FLAG) > 0) {
            getViewModel().getHolderBusiness().refresh()
        } else {
            getViewModel().joinLazyLoad();
        }
    }

    override fun setRefresh(b: Boolean) {}

    override fun engineName(): String? = null

    override fun recycleView(): RecyclerView? {
        return mRecycleViewClient.recycleView()
    }

    override fun normalView(): View? {
        return mRecycleViewClient.normalView()
    }

    override fun refreshView(): SmartRefreshLayout? {
        return mRecycleViewClient.refreshView()
    }

    override fun emptyView(): ViewGroup? {
        return mRecycleViewClient.emptyView()
    }

    var engine: IHolderCollections? = null

    override fun getCollectionsViewEngine(): IHolderCollections? {
        return if (engine == null) {
            SimpleEngine(this).apply {
                engine = this
            }
        } else {
            engine
        }
    }

    protected abstract fun transformDataToList(entity: DATA): List<ITEM>

//    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    abstract fun getRemoteData(index: Int): Observable<DATA>?

    fun createDataEngine(): IDataEngine<DATA>? = null

    override fun getLayoutManger(): RecyclerView.LayoutManager? =
        LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false)


    companion object {
        const val TOTAL_KEY = "vcvf_TOTAL_KEY"
    }


    internal open class SimpleEngine<I, D>(var client: BaseCollectionsViewFragment<*, I, D>) :
        BaseMoreViewEngine<I, D>() {

        override fun myAdapter() =
            client.getAdapter()!!

        override fun getLayoutManger() =
            client.getLayoutManger()

        override fun getRemoteDataReal(page: Int): Observable<D>? =
            client.getRemoteData(page);

        override fun transformDataToList(entity: D): List<I> =
            client.transformDataToList(entity);

    }

}