package com.musongzi.comment.business

import com.musongzi.comment.ad.IAdController
import com.musongzi.comment.ad.IAdLoader
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.holder.IHolderLifecycle
import io.reactivex.rxjava3.core.Observer

/*** created by linhui * on 2022/7/18  */
class SplashBusiness : BaseLifeBusiness<IAgent>(), IAdLoader{

    override fun <T> loadRemoteConfig(observer: Observer<T>) {
        getNext(AdBusiness::class.java)!!.loadRemoteConfig(observer)
    }

    override fun getAdController(): IAdController {
        return getNext(AdBusiness::class.java)!!.getAdController()
    }


}