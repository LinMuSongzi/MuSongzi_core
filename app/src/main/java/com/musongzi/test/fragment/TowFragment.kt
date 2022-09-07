package com.musongzi.test.fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.musongzi.core.ExtensionCoreMethod.getViewModel
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.base.vm.ActivityHelpViewModel
import com.musongzi.core.base.vm.EmployeeEsayViewModel
import com.musongzi.test.databinding.ActivityTowBinding
import com.musongzi.test.vm.TestViewModel
import kotlinx.android.synthetic.main.activity_tow.view.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*** created by linhui * on 2022/7/7 */
class TowFragment : MszFragment<ActivityHelpViewModel, ActivityTowBinding>() {





    override fun initView() {
        EmployeeEsayViewModel::class.java.getViewModel(topViewModelProvider()!!).apply {
            Log.i(TAG, "initView1: $this")
            Log.i(TAG, "initView1: ${getThisLifecycle()}")
        }

        EmployeeEsayViewModel::class.java.getViewModel(topViewModelProvider()!!).apply {
//            isAttachNow()
            Log.i(TAG, "initView2: ${isAttachNow()} , business = $business")
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