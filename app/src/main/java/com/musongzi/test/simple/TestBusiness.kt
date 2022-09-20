package com.musongzi.test.simple

import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.test.vm.BannerViewModel

class TestBusiness : BaseLifeBusiness<BannerViewModel>() {
//    fun checkBanner() {
//        iAgent.getHolderClient()?.showDialog("正在加载")

//        RetrofitManager.getInstance().getApi(Api::class.java).getBannerList().delay(5,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).sub {
//            iAgent.getHolderClient()?.disimissDialog()
//            Log.i(TAG, "checkBanner: liveSaveStateObserver "+it.data[0].name)
//            "Banner".saveStateChange(iAgent,it.toJson())
////            iAgent.getHolderClient()?.showText(it.toJson())
//        }
//    }
}