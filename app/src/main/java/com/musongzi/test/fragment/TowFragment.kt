package com.musongzi.test.fragment

import android.util.Log
import com.musongzi.core.ExtensionCoreMethod.getViewModel
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.base.vm.SimpleHelpClientViewModel
import com.musongzi.core.base.vm.EmployeeEsayViewModel
import com.musongzi.core.base.vm.SimpleViewModel
import com.musongzi.test.databinding.ActivityTowBinding

/*** created by linhui * on 2022/7/7 */
class TowFragment : MszFragment<SimpleViewModel, ActivityTowBinding>() {





    override fun initView() {
        EmployeeEsayViewModel::class.java.getViewModel(topViewModelProvider()!!).apply {
            Log.i(TAG, "initView1: $this")
            Log.i(TAG, "initView1: ${getThisLifecycle()}")
        }

        EmployeeEsayViewModel::class.java.getViewModel(topViewModelProvider()!!).apply {
//            isAttachNow()
            Log.i(TAG, "initView2: ${isAttachNow()} , business = ${getHolderBusiness()}")
            Log.i(TAG, "initView2: ${getThisLifecycle()}")
        }

//        Log.i(TAG, "测量前 dataBinding : ${dataBinding.idText.measuredWidth}")
//        dataBinding.idText.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
//            ,View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED))
//
//        Log.i(TAG, "测量后 dataBinding : ${dataBinding.idText.measuredWidth}")
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            Log.i(TAG, "延迟测量 dataBinding : ${dataBinding.idText.measuredWidth}")
//        },2000)
    }

    override fun initEvent() {

    }

    override fun initData() {

    }
}