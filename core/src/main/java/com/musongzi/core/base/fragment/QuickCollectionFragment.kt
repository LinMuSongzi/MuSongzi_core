package com.musongzi.core.base.fragment

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.itf.page.IPageEngine
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/10/18 */
abstract class QuickCollectionFragment<B : ViewDataBinding, I, D> :
    BaseCollectionsViewFragment<B, I, D>() {

    override fun superDatabindingName() = QuickCollectionFragment::class.java.name

    final override fun updateTitle(aNull: String) {
        Log.i(TAG, "updateTitle: $aNull")
    }

    override fun getPageEngine(): IPageEngine<I>?  = getViewModel().getHolderBusiness().base as? IPageEngine<I>

    final override fun getAdapter(): RecyclerView.Adapter<*>? {
        return getAdapter(getCollectionsViewEngine()?.getPageSupport() as? IPageEngine<I>)
    }

    abstract fun getAdapter(page: IPageEngine<I>?): RecyclerView.Adapter<*>?

    override fun <I> getRefreshClient(): IRefreshClient<I> {
        return this as IRefreshClient<I>
    }

//    override fun createRecycleViewClient(): IRefreshViewClient {
//        TODO("Not yet implemented")
//    }
//
//    override fun transformDataToList(entity: D): List<I> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getRemoteData(index: Int): Observable<D>? {
//        TODO("Not yet implemented")
//    }
}