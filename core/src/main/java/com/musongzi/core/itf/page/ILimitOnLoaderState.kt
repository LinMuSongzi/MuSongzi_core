package com.musongzi.core.itf.page

/*** created by linhui * on 2022/6/29
 * 加载限制的快关设置
 * */
interface ILimitOnLoaderState {

    fun enableRefreshLimit(enable: Boolean)

    fun enableMoreLoadLimit(enable: Boolean)

}