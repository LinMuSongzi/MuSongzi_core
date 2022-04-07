package com.musongzi.core.base.vm

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.base.business.collection.CollectionsBusiness
import com.musongzi.core.base.client.CollectionsViewClient

/**
 * 构建一个自动填充的集合viewmode
 *
 * 由于在 [com.android.playmusic.l.ActivityManager.buildCollectionFragment] 中对viewmodel的 [com.android.playmusic.l.business.listengine.ViewListPageFactory.ENGINE_KEY] key进行了赋值
 * 当次model初始化的时候会独立于各自的Engine作用域
 *
 *
 */
class CollectionsViewModel : MszViewModel<CollectionsViewClient, CollectionsBusiness>(), IRefreshViewModel<Any> {

    lateinit var emptyString: String

    var tags = "CollectionsViewFragment"

    override fun handlerArguments() {
        super.handlerArguments()
        business.handlerArguments(getArguments())
    }

    override fun buildViewByData(datas: List<Any>) {
        client?.buildViewByData(datas)
    }

    override fun setRefresh(b: Boolean) {
        client?.setRefresh(b)
    }


    override fun disimissDialog() {
        client?.disimissDialog()
    }

    override fun notifyDataSetChangedItem(postiont: Int) {
        client?.notifyDataSetChangedItem(postiont)
    }

    override fun getHolderContext(): Context? {
       return super.holderActivity?.getHolderContext()
    }

    override fun getViewModelProvider(thisOrTopProvider: Boolean): ViewModelProvider {
        return client?.getViewModelProvider(thisOrTopProvider)!!
    }


    override fun notifyDataSetChanged() {
        client?.notifyDataSetChanged()
    }

    override fun getBundle(): Bundle? = getArguments()


}