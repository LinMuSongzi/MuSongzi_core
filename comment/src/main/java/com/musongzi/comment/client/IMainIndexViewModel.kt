package com.musongzi.comment.client

import com.musongzi.comment.IMainIndexClient
import com.musongzi.comment.bean.MainIndexBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.MainBottomBusiness
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.*
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.holder.IHolderClient
import com.musongzi.core.itf.page.ISource
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/20 */
interface IMainIndexViewModel: ILifeSaveStateHandle,IAgent, IHandlerChooseViewModel<MainIndexBusiness> {

    override fun getHolderClient():IMainIndexClient?

    fun getSource(): ISource<SimpleCardInfo>

    fun getRemoteMainIndexBean():Observable<MainIndexBean>


}