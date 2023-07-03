package com.musongzi.test.vm

import android.util.Log
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType.ofAll
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.ToastUtils
import com.musongzi.comment.util.GlideEngine
import com.musongzi.comment.viewmodel.ApiViewModel
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.test.IBannerAndRetrofitClient
import com.musongzi.test.MszTestApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList


class BannerViewModel : ApiViewModel<IBannerAndRetrofitClient, EmptyBusiness, MszTestApi>() {


    var textContextField = ObservableField<String>()

    fun loadCheckBanner() {
        showDialog("等待")
//        getApi().grilPic().sub {
//            disimissDialog()
//            Log.i(TAG, "loadCheckBanner:grilPic succed")
//            BANNER_BITMAP_KEY.saveStateChange(localSavedStateHandle(),BitmapFactory.decodeStream(it.byteStream()))
//        }
    }

    fun login(s: String, s1: String) {

//        RetrofitManager.getInstance().getApi(MszTestApi::class.java, this).login2(LoginBean(s, s1))
//            .sub {
//                Log.i(TAG, "login: ${it.toJson()}")
//            }

    }

    fun upload() {

        PictureSelector.create(getHolderClient() as? Fragment)
            .openGallery(ofAll())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setImageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onCancel() {
                    // 取消
                }

                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.let { it ->
                        Log.i(TAG, "onResult: path ${it[0].realPath}")
                        val photoRequestBody: RequestBody =
                            RequestBody.create(MediaType.parse("image/jpg"), File(it[0].realPath))
                        val photo = MultipartBody.Part.createFormData(
                            "headerFile",
                            "headerFile2.jpg",
                            photoRequestBody
                        )
                        RetrofitManager.getInstance().getApi(MszTestApi::class.java, this@BannerViewModel).postPath(photo).sub{ re->
                            Log.i(TAG, "onResult: $re")
                            ToastUtils.showToast(ActivityLifeManager.getInstance().getTopActivity(),"成功")
                        }
                    }
                }
            })
    }


    companion object {
        const val BANNER_BITMAP_KEY = "BANNER_BITMAP_KEY"
        const val BANNER_KEY = "banner_key"
    }


}