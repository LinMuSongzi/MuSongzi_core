package com.heart.core.itf.page

import com.musongzi.core.itf.page.ISource

/*** created by linhui * on 2022/7/25 */
interface IAdpterSource<A, D> : ISource<D> {

    fun getSourceAdapter(): A

}