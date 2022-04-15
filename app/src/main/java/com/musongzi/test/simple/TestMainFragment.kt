package com.musongzi.test.simple

import android.util.Log
import com.musongzi.core.ExtensionMethod.event
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.INext
import com.musongzi.test.ITestClient
import com.musongzi.test.vm.TestViewModel

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient {

    override fun initData() {
        showDialog("")
    }

    override fun initEvent() {
        IClient::class.java.event().disconnect()
    }

    override fun initView() {
//        getMainViewModel().business.checkBanner()
        Log.i(TAG, "initView: ${getMainViewModel()}")
        getMainViewModel()?.business?.checkBanner()
    }

    override fun showText(msg: String) {
        dataBinding.idMainContentTv.text = msg
    }


}