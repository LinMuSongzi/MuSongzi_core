package com.musongzi.test.vm

import android.util.Log
import com.musongzi.comment.bean.MainIndexBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.comment.business.itf.IMainIndexBusiness
import com.musongzi.comment.client.IMainIndexClient
import com.musongzi.comment.viewmodel.itf.IMainIndexViewModel
import com.musongzi.comment.util.SourceImpl
import com.musongzi.comment.viewmodel.ApiViewModel
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

    override fun getRemoteMainIndexBean(): Observable<MainIndexBean> {
        TODO("Not yet implemented")
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