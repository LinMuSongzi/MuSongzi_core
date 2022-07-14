package com.musongzi.core.base.business

import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.business.itf.ISinglePickBusiness
import com.musongzi.core.base.vm.IHandlerChooseViewModel

/**
 * 单选业务
 */
class HandlerChooseBusiness() : BaseLifeBusiness<IHandlerChooseViewModel<*>>() , ISinglePickBusiness {


    constructor(v: IHandlerChooseViewModel<*>) : this() {
        setAgentModel(v)
        afterHandlerBusiness()
    }

    private var pickData: BaseChooseBean? = null

    private fun pick(t: BaseChooseBean?, isPick: Boolean): HandlerChooseBusiness {
        if (t != null) {
            t.choose(isPick)
            if (isPick) {
                pickData = t;
            }
        }
        return this
    }

    fun pickRun(t: BaseChooseBean?) = pick(pickData, t)

    fun pick(unPick: BaseChooseBean?, tPick: BaseChooseBean?): HandlerChooseBusiness {
        pick(unPick, false).pick(tPick, true).iAgent.updateByPick(pickData)
        return this
    }

    fun <T : BaseChooseBean> pickRun(t: T, call: (T) -> Unit) {
        pick(t, true)
        call.invoke(t)
    }


}