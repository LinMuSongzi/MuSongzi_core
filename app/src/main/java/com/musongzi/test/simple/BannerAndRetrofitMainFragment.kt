package com.musongzi.test.simple

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.test.IBannerAndRetrofitClient
import com.musongzi.test.vm.BannerViewModel

open class BannerAndRetrofitMainFragment : MszFragment<BannerViewModel, FragmentTestMainBinding>(), IBannerAndRetrofitClient{

    companion object {
        const val MAX_OUNT = 100_000
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }


    override fun initData() {


        /**
         * 添加基于key的事实观察者（只有党onresume时候才会回调）
         */
        BannerViewModel.BANNER_KEY.liveSaveStateObserver<String>(getViewModel()){
            Log.i(TAG, "liveSaveStateObserver false: 观察到的储存于SavedStateHandler 数据变化是 $it")
            dataBinding.idMainContentTv.text = it
        }

    }

    override fun initView() {

        BannerViewModel.BANNER_BITMAP_KEY.liveSaveStateObserver<Bitmap>(this,getViewModel().localSavedStateHandle()){
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