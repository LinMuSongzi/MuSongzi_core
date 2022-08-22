package com.musongzi.spi

/*** created by linhui * on 2022/8/21 */
interface IStrategyRule {
    fun onLoadRule(request: ISpiRequest):Class<*>
}