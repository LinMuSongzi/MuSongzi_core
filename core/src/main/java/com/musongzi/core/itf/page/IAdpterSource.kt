package com.musongzi.core.itf.page

/*** created by linhui * on 2022/7/25 */
interface IAdpterSource<A, D> : ISource<D> {

    fun getSourceAdapter(): A

}