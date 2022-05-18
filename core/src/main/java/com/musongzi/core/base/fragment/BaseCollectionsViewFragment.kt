package com.musongzi.core.base.fragment

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
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

abstract class BaseCollectionsViewFragment<B : ViewDataBinding, ITEM : BaseChooseBean, DATA> :
    LRefreshFrament<CollectionsViewModel, B, ITEM>(), CollectionsViewClient<ITEM>,
    CollectionsViewSupport {

    override fun actualTypeArgumentsViewModelIndex() = 0
    override fun actualTypeArgumentsDatabindinIndex(): Int = 0
    override fun superFragmentName() = LRefreshFrament::class.java.name
    override fun superDatabindingName() = BaseCollectionsViewFragment::class.java.name

    private lateinit var mRecycleViewClient: IRefreshViewClient

    abstract fun createRecycleViewClient(): IRefreshViewClient

    override fun initView() {
        getViewModel().business.handlerArguments(arguments)
    }

    override fun handlerArguments() {
        super.handlerArguments()
        mRecycleViewClient = createRecycleViewClient()
    }

    override fun initEvent() {
        (getViewModel().getHolderBusiness().base as? IHolderCollections)?.onRefreshViewClientEvent(
            mRecycleViewClient
        );
    }

    override fun initData() {
        getViewModel().getHolderBusiness().refresh()
    }

    override fun setRefresh(b: Boolean) {}

    override fun engineName() = null

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
        return mRecycleViewClient.recycleView()
    }

    override fun getCollectionsViewEngine(): IHolderCollections? {
        return object : BaseMoreViewEngine<ITEM, DATA>() {

            override fun myAdapter() =
                this@BaseCollectionsViewFragment.getAdapter()!!

            override fun getLayoutManger() =
                this@BaseCollectionsViewFragment.getLayoutManger()

            override fun getRemoteDataReal(page: Int): Observable<DATA>? =
                this@BaseCollectionsViewFragment.getRemoteData(page);

            override fun transformDataToList(entity: DATA): List<ITEM> =
                this@BaseCollectionsViewFragment.transformDataToList(entity);

        }
    }

    protected abstract fun transformDataToList(entity: DATA): List<ITEM>

    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    fun getRemoteData(index: Int): Observable<DATA>? = null

    fun createDataEngine(): IDataEngine<DATA>? = null

    override fun getLayoutManger(): RecyclerView.LayoutManager? = null

}