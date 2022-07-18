package com.musongzi.comment.business

import com.musongzi.comment.ad.IAdController
import com.musongzi.comment.ad.IAdLoader
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IAgent
import io.reactivex.rxjava3.core.Observer

/*** created by linhui * on 2022/7/18 */
class AdBusiness: BaseLifeBusiness<IAgent>() , IAdLoader {
    override fun <T> loadRemoteConfig(observer: Observer<T>) {
        TODO("Not yet implemented")
    }

    override fun getAdController(): IAdController {
        TODO("Not yet implemented")
    }


}