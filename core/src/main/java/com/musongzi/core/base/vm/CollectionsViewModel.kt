package com.musongzi.core.base.vm

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.business.collection.CollectionsBusiness
import com.musongzi.core.base.business.collection.ViewListPageFactory
import com.musongzi.core.base.client.CollectionsViewClient
import com.musongzi.core.base.client.IRefreshClient
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.holder.IHolderViewModelProvider

/**
 * 构建一个自动填充的集合viewmode
 *
 * 由于在 [buildCollectionFragment] 中对viewmodel的 key进行了赋值
 * 当次model初始化的时候会独立于各自的Engine作用域
 *
 *
 */
class CollectionsViewModel : MszViewModel<CollectionsViewClient, CollectionsBusiness>(),
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

    override fun getHolderClient(): CollectionsViewClient? {
        return super.getHolderClient()
    }

//    override fun buildViewByData(datas: List<Any>) {
//        client?.getRefreshClient<Any>()?.buildViewByData(datas)
//    }
//
//    override fun setRefresh(b: Boolean) {
//        client?.getRefreshClient<Any>()?.setRefresh(b)
//    }


    override fun disimissDialog() {
        getHolderClient()?.getRefreshClient<Any>()?.disimissDialog()
    }

//    override fun notifyDataSetChangedItem(postiont: Int) {
//        client?.getRefreshClient<Any>()?.notifyDataSetChangedItem(postiont)
//    }

    override fun getHolderContext(): Context? {
        return super.holderActivity?.get()?.getHolderContext()
    }

//    override fun getViewModelProvider(thisOrTopProvider: Boolean): ViewModelProvider {
//        return client?.getViewModelProvider(thisOrTopProvider)!!
//    }


//    override fun notifyDataSetChanged() {
//        client?.getRefreshClient<Any>()?.notifyDataSetChanged()
//    }

    override fun getBundle(): Bundle? = getArguments()

    /**
     * 一个集合引擎参数信息
     */
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

        /**
         * 是否开启下拉刷新
         */
        var isEnableReFresh = true

        /**
         * 是否开启加载更多
         */
        var isEnableLoadMore = true

        /**
         * 是否注册eventbus
         */
        var isEnableEventBus = false

        /**
         * 标题
         */
        var title: String = ""

        /**
         * 无数据加载时要显示的布局提（有默认）
         */
        var emptyLoadRes = 0

        /**
         * 制定当前的 viewmodel 的 key
         */
        var modelKey: String = ""

        /**
         * 无数据时默认情况下ui提示的语言
         */
        var emptyString: String = ""

        /**
         * 最核心的注入对象
         * 继承于 [com.musongzi.core.base.business.collection.BaseMoreViewEngine]
         */
        var engineName: String = ""

        /**
         * 是否开启懒加载
         * 默认不是
         * 开启懒加载后，第一次打开不会自动加载数据
         * [LAZY_LOAD_OPEN_FLAG]
         * [LAZY_LOAD_CLOSE_FLAG]
         */
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

    override fun updateByPick(info: IChoose?) {
//        notifyDataSetChanged()
        getRefreshClient()?.notifyDataSetChanged()
    }

    fun joinLazyLoad() {
        Log.i(TAG, "joinLazyLoad: " + hashCode())
    }

    companion object {
        /**
         * 集合引擎，懒加载开启
         */
        const val LAZY_LOAD_OPEN_FLAG = 0x101

        /**
         * 集合引擎，懒加载关闭
         */
        const val LAZY_LOAD_CLOSE_FLAG = 0x102

    }

    override fun getRefreshClient(): IRefreshClient<Any>? {
        return getHolderClient()?.getRefreshClient()
    }

    override fun getHolderViewModelProvider(): IHolderViewModelProvider? {
        return getHolderClient()
    }

}