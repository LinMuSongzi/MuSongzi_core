package com.musongzi.core.base.business

import com.musongzi.core.itf.IOnClickAction
import com.musongzi.core.itf.IViewInstance

/*** created by linhui * on 2022/9/13 */
abstract class BaseOnClickAction(var action: String?) : BaseMapBusiness<IViewInstance>(),
    IOnClickAction {


}