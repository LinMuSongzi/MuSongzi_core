package com.musongzi.test.simple

import android.graphics.Bitmap
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.test.IBannerAndRetrofitClient
import com.musongzi.test.databinding.FragmentBannerCheckBinding
import com.musongzi.test.vm.BannerViewModel

open class BannerAndRetrofitMainFragment :
    MszFragment<BannerViewModel, FragmentBannerCheckBinding>(), IBannerAndRetrofitClient {

    /*
     */

    companion object {
        const val MAX_OUNT = 100_000
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }


    override fun initData() {

        dataBinding.viewModel = getViewModel()
        Log.i(TAG, "onPropertyChanged: field =  "+getViewModel().textContextField)
        /**
         * 添加基于key的事实观察者（只有党onresume时候才会回调）
         */
        BannerViewModel.BANNER_KEY.liveSaveStateObserver<String>(getViewModel()) {
            Log.i(TAG, "liveSaveStateObserver false: 观察到的储存于SavedStateHandler 数据变化是 $it")
            dataBinding.idMainContentTv.text = it
        }

        getViewModel().textContextField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.i(TAG, "onPropertyChanged: $propertyId , sender  =  $sender")
            }
        })

        dataBinding.idMainContentTv.setOnClickListener{
            getViewModel().upload()
        }

        getViewModel().login("denglu","123123")


//        Thread {
//            var sum = 0;
//            while (true) {
//                Thread.sleep(1000)
//                if (!isDetached && isAdded) {
//                    runOnUiThread {
//
////                    Log.i(TAG, "initData: sum thread  =  $sum")
//                        getViewModel().textContextField.set((++sum).toString())
//                    }
//                }
//            }
//        }.start()

    }

    override fun initView() {

        BannerViewModel.BANNER_BITMAP_KEY.liveSaveStateObserver<Bitmap>(
            this,
            getViewModel().localSavedStateHandle()
        ) {
            setGrilPicture(it)
        }

        getViewModel().loadCheckBanner()
    }

    override fun initEvent() {
    }

    override fun setGrilPicture(decodeByteArray: Bitmap?) {
        dataBinding.idPic.setImageBitmap(decodeByteArray)
    }


}