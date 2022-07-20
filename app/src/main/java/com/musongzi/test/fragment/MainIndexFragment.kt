package com.musongzi.test.fragment

import com.musongzi.comment.business.MainBottomBusiness
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.test.databinding.FragmentMainIndexBinding
import com.musongzi.test.vm.MainIndexViewModel

/*** created by linhui * on 2022/7/20 */
class MainIndexFragment : ModelFragment<MainIndexViewModel, FragmentMainIndexBinding>() {

    override fun initView() {
        dataBinding.idBottomView.initMainBottomView(
            getViewModel(),
            MainBottomBusiness.narmalMainArrayInfo(getViewModel())
        )
    }

    override fun initEvent() {
    }

    override fun initData() {

    }
}