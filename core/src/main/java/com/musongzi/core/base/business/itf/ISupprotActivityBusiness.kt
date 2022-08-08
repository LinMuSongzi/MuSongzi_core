package com.musongzi.core.base.business.itf

import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.holder.IHolderContext

/*** created by linhui * on 2022/7/6 */
interface ISupprotActivityBusiness :IBusiness,IHolderContext,IDisconnect{
    fun checkEvent()
}