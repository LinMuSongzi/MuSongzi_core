package com.musongzi.test.vm

import android.util.Log
import android.view.View
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.R
import com.musongzi.comment.bean.ImageLoadBean
import com.musongzi.comment.bean.MainIndexBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.MainBottomBusiness
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.comment.business.itf.IMainIndexBusiness
import com.musongzi.comment.client.IMainIndexClient
import com.musongzi.comment.viewmodel.itf.IMainIndexViewModel
import com.musongzi.comment.util.SourceImpl
import com.musongzi.comment.viewmodel.ApiViewModel
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.InjectionHelp
import com.musongzi.spi.ISpiRequest
import com.musongzi.spi.SpiManger
import com.musongzi.test.Api
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/20 */
class MainIndexViewModel : ApiViewModel<IMainIndexClient, IMainIndexBusiness, Api>(),
    IMainIndexViewModel, ISpiRequest {

    override fun createBusiness2(): IMainIndexBusiness {
        val l =  (SpiManger.loadInstance(this) as? IMainIndexBusiness)!!
//        Log.i(TAG, "createBusiness2: ")
        return l;
    }

    private val s: ISource<SimpleCardInfo> by lazy {
        SourceImpl()
    }

    override fun getSource(): ISource<SimpleCardInfo> {
        return s
    }

    override fun getRemoteMainIndexBean(): Observable<Array<SimpleCardInfo>> {
       return Observable.create {
            it.onNext(normalMainArrayInfo(this@MainIndexViewModel))
        }
    }



    private fun String.buildInfo(
        colors: Pair<Int, Int>,
        images: Pair<Any, Any>,
        onClick: (View) -> Unit,
    ): SimpleCardInfo {
        return SimpleCardInfo(
            this,
            colors,
            ImageLoadBean(images.first) to ImageLoadBean(images.second),
            onClick
        )
    }

    protected open fun tatol() = 4

    protected open fun normalMainArrayInfo(holderSavedStateHandle: IHolderSavedStateHandle): Array<SimpleCardInfo> {
        val titleArray: Array<String> = buildTitle();
        val imageArray: Array<Pair<Any, Any>> = buildImage()
        val colorPair: Pair<Int, Int> = buildColorPair();
        val t = tatol()
        return Array(t) { index ->
            titleArray[index].buildInfo(
                colorPair,
                imageArray[index]
            ) {
                /**
                 * 点击事件触发
                 */
                MainBottomBusiness.INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, index)
            }
        }
    }

    protected open fun buildImage(): Array<Pair<Any, Any>> {
        return arrayOf(
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher
        )
    }

    protected open fun buildColorPair(): Pair<Int, Int> {
        return R.color.text_color_unSelect to R.color.text_color_select
    }

    protected open fun buildTitle(): Array<String> {
        return arrayOf("推荐", "关注", "活动", "我的")
    }

    override fun updateByPick(pickData: IChoose?) {

    }

    override fun getRequestLoaderClass(): Class<*> {
        return IMainIndexClient::class.java
    }

    override fun orderName(): String {
      return  MainIndexViewModel::class.java.name
    }


}