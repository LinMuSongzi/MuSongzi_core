package com.musongzi.test

import com.musongzi.spi.Factory
import com.musongzi.spi.ISpiRequest
import com.musongzi.spi.IStrategyRule
import com.musongzi.test.vm.MainIndexViewModel
import com.musongzi.test.vm.TestMainIndexBusiness

/*** created by linhui * on 2022/8/21 */
class MyRuleProxy : IStrategyRule {

    //val instance: IStrategyRule = Factory.createDefualtStrategyRule()

    override fun onLoadRule(request: ISpiRequest): Class<*> {
        return when (request.orderName()) {
            MainIndexViewModel::class.java.name ->
                TestMainIndexBusiness::class.java
            else -> {
                TODO("error")
            }
        }
    }


}