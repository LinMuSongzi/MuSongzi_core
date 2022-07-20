package com.musongzi.test.vm

import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.IMainIndexClient
import com.musongzi.comment.bean.MainIndexBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.MainBottomBusiness
import com.musongzi.core.base.vm.EasyViewModel
import com.musongzi.core.itf.IClient
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.comment.client.IMainIndexViewModel
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.ExtensionCoreMethod.wantPick
import com.musongzi.core.itf.data.IChoose
import com.musongzi.core.itf.page.ISource
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/7/20 */
class MainIndexViewModel : EasyViewModel<IMainIndexClient?, MainIndexBusiness>(),IMainIndexViewModel {

    private val s:ISource<SimpleCardInfo> by lazy {
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

}