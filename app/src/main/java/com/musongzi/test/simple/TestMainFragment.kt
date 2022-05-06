package com.musongzi.test.simple

import android.provider.Contacts
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.IClient
import com.musongzi.test.ITestClient
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Math.abs
import java.text.SimpleDateFormat

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient ,ILoginEvent{

    companion object
    {
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }

    override fun initData() {
//        showDialog("")
    }


    override fun initEvent() {
//        Thread {
            val sl = System.currentTimeMillis()
            activity?.runOnUiThread {
                Log.i(TAG, "initEvent: start ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}")
            }
//            for (v in 1..1_000_000) {
                ILoginEvent::class.java.event()?.onLogin()
//                EventBus.getDefault().post(DiscoverBannerBean())
//            }
            val el = System.currentTimeMillis()
            activity?.runOnUiThread {
                Log.i(TAG, "initEvent:   end ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}")
                Log.i(TAG, "initEvent: ${abs(sl - el)}")
            }

//        }.start()

    }

    var count = 0

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean){
        count++
        if(count == 1000000){
            Log.i(TAG, "initEvent: $count")
        }
    }

    override fun showDialog(msg: String?) {
//        super.showDialog(msg)
//        Log.i(TAG, "EventManger showDialog: msg = $msg ")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        registerEvent(ILoginEvent::class.java) {
            this
        }

        Log.i(TAG, "initView: ${getMainViewModel()}")
        getMainViewModel()?.business?.checkBanner()
    }

    override fun showText(msg: String) {
        dataBinding.idMainContentTv.text = msg
    }

    override fun onLogin() {
        count++
        if(count == 1000000){
            Log.i(TAG, "onLogin initEvent: $count")
        }else{
            Log.i(TAG, "onLogin initEvent: $count")
        }
    }

    override fun onLogout() {

    }


}