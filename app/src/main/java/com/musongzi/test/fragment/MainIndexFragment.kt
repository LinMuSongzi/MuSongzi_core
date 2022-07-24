package com.musongzi.test.fragment

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.musongzi.comment.client.IMainIndexClient
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.test.databinding.FragmentMainIndexBinding
import com.musongzi.test.vm.MainIndexViewModel

/*** created by linhui * on 2022/7/20 */
class MainIndexFragment : ModelFragment<MainIndexViewModel, FragmentMainIndexBinding>(),
    IMainIndexClient {

    override fun initView() {
        getViewModel().business.buildDataBySize()
    }

    override fun initEvent() {

    }

    override fun initData() {

    }

    override fun getRecycleView(): RecyclerView = dataBinding.idBottomView

    override fun getViewpage2(): ViewPager2 = dataBinding.idViewpage
}
