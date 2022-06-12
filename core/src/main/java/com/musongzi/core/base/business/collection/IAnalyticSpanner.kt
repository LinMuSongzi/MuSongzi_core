package com.musongzi.core.base.business.collection

interface IAnalyticSpanner<I, D> {

    fun useSpanner(data: D): I?

}