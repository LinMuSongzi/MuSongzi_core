package com.musongzi.test.simple

import android.util.Log
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserverCall
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.ExtensionCoreMethod.toJson
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.test.ITestClient
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Math.abs
import java.text.SimpleDateFormat

class TestMainFragment : MszFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient{

    companion object {
        const val MAX_OUNT = 100_000
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }


    override fun initData() {

        val testKey = "Banner"
//
        /**
         * 添加基于key的事实观察者（只有党onresume时候才会回调）
         */
        testKey.liveSaveStateObserver<String>(getViewModel()){
            Log.i(TAG, "liveSaveStateObserver false: 观察到的储存于SavedStateHandler 数据变化是 $it")
            dataBinding.idMainContentTv.text = it
        }

    }

    override fun initView() {
        getViewModel().business.checkBanner()
    }

    override fun initEvent() {
    }


}