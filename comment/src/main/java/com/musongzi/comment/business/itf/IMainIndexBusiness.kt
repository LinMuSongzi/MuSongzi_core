package com.musongzi.comment.business.itf

import com.musongzi.core.itf.IBusiness

/*** created by linhui * on 2022/7/25 */
interface IMainIndexBusiness:IBusiness {
    fun buildDataBySize()
    fun sizeMax():Byte
}