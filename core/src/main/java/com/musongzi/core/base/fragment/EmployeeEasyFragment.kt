package com.musongzi.core.base.fragment

import android.view.View
import androidx.databinding.ViewDataBinding
import com.musongzi.core.base.vm.EsayViewModel

/*** created by linhui * on 2022/8/16 */
class EmployeeEasyFragment: MszFragment<EsayViewModel,ViewDataBinding>() {

    override fun getLayoutId(): Int {
        return View.NO_ID
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initEvent() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}