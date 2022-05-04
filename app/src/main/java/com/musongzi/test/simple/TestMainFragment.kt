package com.musongzi.test.simple

import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.test.ITestClient
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient,
    ILoginEvent {

    override fun initData() {
//        showDialog("")
    }

    override fun initEvent() {
        Thread {
            val s = System.currentTimeMillis()
            for (v in 1..200000) {
                ILoginEvent::class.java.event()?.onLogin()
            }
            Log.i(TAG, "initEvent: " + (System.currentTimeMillis() - s)/1000)
        }.start()

    }

    override fun showDialog(msg: String?) {
        super.showDialog(msg)
        Log.i(TAG, "EventManger showDialog: msg = $msg ")
    }


    override fun initView() {

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
        //  Log.i(TAG, "onLogine: TestMainFragment")
    }

    override fun onLogout() {
        // Log.i(TAG, "onLogout: TestMainFragment")
    }


}