package com.musongzi.core.base.vm

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.business.collection.CollectionsBusiness
import com.musongzi.core.base.business.collection.ViewListPageFactory
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.itf.page.ISource

/**
 * 构建一个自动填充的集合viewmode
 *
 * 由于在 [buildCollectionFragment] 中对viewmodel的 key进行了赋值
 * 当次model初始化的时候会独立于各自的Engine作用域
 *
 *
 */
class CollectionsViewModel : EasyViewModel<CollectionsViewClient<Any>, CollectionsBusiness>(),
    IHandlerChooseViewModel<CollectionsBusiness>, IRefreshViewModel<Any> {

//    lateinit var emptyString: String

    lateinit var collectionsInfo: CollectionsInfo
    var tags = "CollectionsViewFragment"

    override fun handlerArguments() {
        super.handlerArguments()
        collectionsInfo = if (getArguments() == null) {
            CollectionsInfo()
        } else {
            getArguments()!!.getParcelable(ViewListPageFactory.INFO_KEY)!!
        }

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

    class CollectionsInfo() : Parcelable {

        constructor(c: CollecttionsEngine?) : this() {
            c?.let {
                isEnableEventBus = it.isEnableEventBus
                isEnableLoadMore = it.isEnableLoadMore
                isEnableEventBus = it.isEnableEventBus
                title = it.title
                emptyLoadRes = it.emptyLoadRes
                modelKey = it.modelKey
                emptyString = it.emptyString
                openLazyLoad = if (it.openLazyLoad) LAZY_LOAD_OPEN_FLAG else LAZY_LOAD_CLOSE_FLAG
            }
        }

        var isEnableReFresh = true
        var isEnableLoadMore = true
        var isEnableEventBus = true
        var title: String = ""
        var emptyLoadRes = 0
        var modelKey: String = ""
        var emptyString: String = ""
        var engineName: String = ""
        var openLazyLoad = LAZY_LOAD_CLOSE_FLAG


        constructor(parcel: android.os.Parcel) : this() {
            isEnableReFresh = parcel.readByte() != 0.toByte()
            isEnableLoadMore = parcel.readByte() != 0.toByte()
            isEnableEventBus = parcel.readByte() != 0.toByte()
            title = parcel.readString()!!
            emptyLoadRes = parcel.readInt()
            modelKey = parcel.readString()!!
            emptyString = parcel.readString()!!
            engineName = parcel.readString()!!
            openLazyLoad = parcel.readInt()
        }

        override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
            parcel.writeByte(if (isEnableReFresh) 1 else 0)
            parcel.writeByte(if (isEnableLoadMore) 1 else 0)
            parcel.writeByte(if (isEnableEventBus) 1 else 0)
            parcel.writeString(title)
            parcel.writeInt(emptyLoadRes)
            parcel.writeString(modelKey)
            parcel.writeString(emptyString)
            parcel.writeString(engineName)
            parcel.writeInt(openLazyLoad)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CollectionsInfo> {
            override fun createFromParcel(parcel: android.os.Parcel): CollectionsInfo {
                return CollectionsInfo(parcel)
            }

            override fun newArray(size: Int): Array<CollectionsInfo?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun baseDatas(): ISource<BaseChooseBean>? {
        return business.base as? ISource<BaseChooseBean>
    }

    override fun updateByPick(info: BaseChooseBean?) {
        notifyDataSetChanged()
    }

    fun joinLazyLoad() {
        Log.i(TAG, "joinLazyLoad: " + hashCode())
    }

    companion object {

        const val LAZY_LOAD_OPEN_FLAG = 0x101
        const val LAZY_LOAD_CLOSE_FLAG = 0x102

    }

}