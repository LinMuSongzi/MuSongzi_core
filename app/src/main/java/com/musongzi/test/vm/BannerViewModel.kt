package com.musongzi.test.vm

import android.graphics.BitmapFactory
import android.util.Log
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.test.IBannerAndRetrofitClient
import com.musongzi.comment.viewmodel.ApiViewModel
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.test.Api

class BannerViewModel : ApiViewModel<IBannerAndRetrofitClient, EmptyBusiness, Api>() {


    fun loadCheckBanner() {
        showDialog("等待")
        getApi().grilPic().sub {
            disimissDialog()
            Log.i(TAG, "loadCheckBanner:grilPic succed")
            BANNER_BITMAP_KEY.saveStateChange(localSavedStateHandle(),BitmapFactory.decodeStream(it.byteStream()))
        }
    }

    companion object {
        const val BANNER_BITMAP_KEY ="BANNER_BITMAP_KEY"
        const val BANNER_KEY = "banner_key"
    }


}