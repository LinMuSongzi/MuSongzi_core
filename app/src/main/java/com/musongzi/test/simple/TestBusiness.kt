package com.musongzi.test.simple

import android.util.Log
import com.musongzi.core.ExtensionMethod.sub
import com.musongzi.core.ExtensionMethod.toJson
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.test.Api
import com.musongzi.test.vm.TestViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TestBusiness : BaseLifeBusiness<TestViewModel>() {
    fun checkBanner() {
        iAgent.getApi().getBannerList().sub {
            iAgent.getHolderClient()?.showText(it.toJson())
        }
    }

}