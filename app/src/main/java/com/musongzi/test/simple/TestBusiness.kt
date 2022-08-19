package com.musongzi.test.simple

import android.util.Log
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.ExtensionCoreMethod.toJson
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.test.Api
import com.musongzi.test.vm.TestViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class TestBusiness : BaseLifeBusiness<TestViewModel>() {
    fun checkBanner() {
        iAgent.getHolderClient()?.showDialog("正在加载")

        RetrofitManager.getInstance().getApi(Api::class.java).getBannerList().delay(5,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).sub {
            iAgent.getHolderClient()?.disimissDialog()
            Log.i(TAG, "checkBanner: liveSaveStateObserver "+it.data[0].name)
            "Banner".saveStateChange(iAgent,it.toJson())
//            iAgent.getHolderClient()?.showText(it.toJson())
        }
    }
}