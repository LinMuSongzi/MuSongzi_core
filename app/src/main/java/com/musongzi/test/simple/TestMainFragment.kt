package com.musongzi.test.simple

import android.provider.Contacts
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.IClient
import com.musongzi.test.ITestClient
import com.musongzi.test.vm.TestViewModel

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient {

    override fun initData() {
//        showDialog("")
    }

    override fun initEvent() {
        Thread {
            val sl = System.currentTimeMillis()
            Log.i(TAG, "initEvent: start $sl")
//            for (v in 1..10_000_000) {
                ITestClient::class.java.event()?.showDialog("哈哈哈")
//            }
            val el = System.currentTimeMillis()
            Log.i(TAG, "initEvent:   end $el")
            Log.i(TAG, "initEvent: ${sl - el}")
        }.start()

    }

    override fun showDialog(msg: String?) {
//        super.showDialog(msg)
//        Log.i(TAG, "EventManger showDialog: msg = $msg ")
    }


    override fun initView() {

        registerEvent(IClient::class.java) {
            this
        }

        Log.i(TAG, "initView: ${getMainViewModel()}")
        getMainViewModel()?.business?.checkBanner()
    }

    override fun showText(msg: String) {
        dataBinding.idMainContentTv.text = msg
    }


}