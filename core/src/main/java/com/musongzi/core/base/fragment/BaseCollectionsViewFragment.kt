package com.musongzi.core.base.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.CollectionsViewSupport
import com.musongzi.core.base.business.collection.IHolderCollections
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.itf.page.IDataEngine
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.Observable

abstract class BaseCollectionsViewFragment<B : ViewDataBinding, ITEM, DATA> : LRefreshFrament<CollectionsViewModel, B, Any>(), CollectionsViewClient, CollectionsViewSupport {

    private var mRecycleViewClient: IRefreshViewClient = createRecycleViewClient()

    abstract fun createRecycleViewClient(): IRefreshViewClient

    override fun initEvent() {
        (getMainViewModel()?.getHolderBusiness()?.base as? IHolderCollections)?.onRefreshViewClientEvent(
            mRecycleViewClient
        );
    }

    override fun initData() {
        getMainViewModel()?.getHolderBusiness()?.refresh()
    }

    override fun setRefresh(b: Boolean) {}

    override fun engineName() = null

    companion object {
        @kotlin.jvm.JvmField
        var BUNDLE_KEY = "bundle_key"
    }

    override fun recycleView(): RecyclerView? {
        return mRecycleViewClient.recycleView()
    }

    override fun normalView(): View? {
        return mRecycleViewClient.normalView()
    }

    override fun refreshView(): SmartRefreshLayout? {
        return mRecycleViewClient.refreshView()
    }

    override fun getCollectionsViewEngine(): IHolderCollections? {
        return object : BaseMoreViewEngine<ITEM, DATA>() {

            override fun myAdapter() =
                this@BaseCollectionsViewFragment.getAdapter()!!

            override fun getLayoutManger() =
                this@BaseCollectionsViewFragment.getLayoutManger()

            override fun getRemoteDataReal(index: Int): Observable<DATA>? =
                this@BaseCollectionsViewFragment.getRemoteData(index);

            override fun transformDataToList(entity: DATA): List<ITEM> =
                this@BaseCollectionsViewFragment.transformDataToList(entity);

        }
    }

    protected abstract fun transformDataToList(entity: DATA): List<ITEM>

    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    fun getRemoteData(index: Int): Observable<DATA>? = null

    fun createDataEngine(): IDataEngine<DATA>? = null

    override fun getLayoutManger(): RecyclerView.LayoutManager? = null

    override fun superDatabindingName(): String = BaseCollectionsViewFragment::class.java.name

    override fun actualTypeArgumentsDatabindinIndex(): Int = 0

}