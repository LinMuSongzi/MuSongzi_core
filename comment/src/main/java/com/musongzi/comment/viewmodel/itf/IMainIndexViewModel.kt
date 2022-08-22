package com.musongzi.comment.viewmodel.itf

import com.musongzi.comment.bean.MainIndexBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.comment.business.itf.IMainIndexBusiness
import com.musongzi.comment.client.IMainIndexClient
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.itf.holder.IHolderContext
import com.musongzi.core.itf.page.ISource
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/20 */
interface IMainIndexViewModel: ILifeSaveStateHandle,IAgent, IHandlerChooseViewModel<IMainIndexBusiness>{

    override fun getHolderClient(): IMainIndexClient?

    fun getSource(): ISource<SimpleCardInfo>

    fun getRemoteMainIndexBean():Observable<Array<SimpleCardInfo>>


//    fun getHolder

}