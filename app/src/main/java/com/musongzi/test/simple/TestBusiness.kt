package com.musongzi.test.simple

import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.ExtensionCoreMethod.toJson
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.test.vm.TestViewModel

class TestBusiness : BaseLifeBusiness<TestViewModel>() {
    fun checkBanner() {
        iAgent.getHolderClient()?.showDialog("正在加载")

//        iAgent.getApi().getBannerList().sub {
//            iAgent.getHolderClient()?.disimissDialog()
//            iAgent.getHolderClient()?.showText(it.toJson())
//        }
    }
}