package com.musongzi.spi

import com.musongzi.core.base.manager.ManagerInstanceHelp
import java.lang.ref.WeakReference

/*** created by linhui * on 2022/8/21 */
object Factory {


    fun <T : IStrategyRule> spiManagerHelp(spiRule: Class<T>): ManagerInstanceHelp {
        return SpiManager.ManagerInstanceHelpImpl(WeakReference(spiRule))
    }

    fun createDefualtStrategyRule(): IStrategyRule {
        TODO("Not yet implemented")
    }


}