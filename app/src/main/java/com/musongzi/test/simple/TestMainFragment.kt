package com.musongzi.test.simple

import android.provider.Contacts
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.IClient
import com.musongzi.test.ITestClient
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel
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
        Thread {
            val sl = System.currentTimeMillis()
            Log.i(TAG, "initEvent: start ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}")
            for (v in 1..1_000_000) {
                ILoginEvent::class.java.event()?.onLogin()
            }
            val el = System.currentTimeMillis()
            Log.i(TAG, "initEvent:   end ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}")
            Log.i(TAG, "initEvent: ${abs(sl - el)}")
        }.start()

    }

    override fun showDialog(msg: String?) {
//        super.showDialog(msg)
//        Log.i(TAG, "EventManger showDialog: msg = $msg ")
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
//        Log.i(TAG, "onLogin: 1")
//        var a = 0
//        a++
//        Log.i(TAG, "onLogin: $a")
    }

    override fun onLogout() {

    }


}