package com.musongzi.test.simple

import android.provider.Contacts
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.INext
import com.musongzi.test.ITestClient
import com.musongzi.test.vm.TestViewModel

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient {

    override fun initData() {
//        showDialog("")
    }

    override fun initEvent() {
        IClient::class.java.event()?.showDialog("哈哈哈")
    }

    override fun showDialog(msg: String?) {
        super.showDialog(msg)
        Log.i(TAG, "EventManger showDialog: msg = $msg ")
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