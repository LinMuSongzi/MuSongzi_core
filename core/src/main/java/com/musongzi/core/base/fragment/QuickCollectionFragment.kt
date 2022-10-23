package com.musongzi.core.base.fragment

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/10/18 */
abstract class QuickCollectionFragment<B : ViewDataBinding, I, D> :
    BaseCollectionsViewFragment<B, I, D>() {

    override fun superDatabindingName() = QuickCollectionFragment::class.java.name

    final override fun updateTitle(aNull: String) {
        Log.i(TAG, "updateTitle: $aNull")
    }

    override fun getPageEngine(): IPageEngine<I>? {
        return if(lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)){
            getCollectionsViewEngine() as? IPageEngine<I>?
        }else{
            null
        }
    }

    final override fun getAdapter(): RecyclerView.Adapter<*>? {
        return getAdapter(getCollectionsViewEngine() as? ISource<I>)
    }

    abstract fun getAdapter(page: ISource<I>?): RecyclerView.Adapter<*>?

    override fun <I> getRefreshClient(): IRefreshClient<I> {
        return this as IRefreshClient<I>
    }
}