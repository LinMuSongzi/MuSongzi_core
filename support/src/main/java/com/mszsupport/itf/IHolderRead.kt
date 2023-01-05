package com.mszsupport.itf

import com.mszsupport.itf.page.IRead

/*** created by linhui * on 2022/8/11 */
interface IHolderRead<I: IRead> {

    fun getHolderRead():I

}