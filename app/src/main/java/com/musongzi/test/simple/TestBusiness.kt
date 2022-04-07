package com.musongzi.test.simple

import android.util.Log
import com.musongzi.core.ExtensionMethod.sub
import com.musongzi.core.ExtensionMethod.toJson
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.test.vm.TestViewModel

class TestBusiness : BaseLifeBusiness<TestViewModel>() {
    fun checkBanner() {
        iAgent.getApi().getBannerList().sub{
            iAgent.getHolderClient()?.showDialog(it.toJson())
        }
    }

}