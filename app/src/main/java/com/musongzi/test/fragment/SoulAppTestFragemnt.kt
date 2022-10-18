package com.musongzi.test.fragment

import com.musongzi.CONFIG_MANAGER
import com.musongzi.ConfigManager
import com.musongzi.comment.viewmodel.EsayApiViewModel
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.core.base.manager.ManagerUtil.manager
import com.musongzi.test.MszTestApi
import com.musongzi.test.databinding.FragmentSoulAppTestBinding

/*** created by linhui * on 2022/10/17 */
class SoulAppTestFragemnt :MszFragment<EsayApiViewModel<MszTestApi>,FragmentSoulAppTestBinding>(){

    override fun initView() {
        CONFIG_MANAGER.manager<ConfigManager>().showConfig();

    }

    override fun initEvent() {
    }

    override fun initData() {
    }
}