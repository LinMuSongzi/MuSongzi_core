package com.musongzi.core.base.business

import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.business.itf.ISinglePickBusiness
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.data.IChoose

/**
 * 单选业务
 */
class HandlerChooseBusiness() : BaseLifeBusiness<IHandlerChooseViewModel<*>>() , ISinglePickBusiness {


    constructor(v: IHandlerChooseViewModel<*>) : this() {
        setAgentModel(v)
        afterHandlerBusiness()
    }

    private var pickData: IChoose? = null

    private fun pick(t: IChoose?, isPick: Boolean): HandlerChooseBusiness {
        if (t != null) {
            t.choose(isPick)
            if (isPick) {
                pickData = t;
            }
        }
        return this
    }

    fun pickRun(t: IChoose?) = pick(pickData, t)

    fun pick(unPick: IChoose?, tPick: IChoose?): HandlerChooseBusiness {
        pick(unPick, false).pick(tPick, true).iAgent.updateByPick(pickData)
        return this
    }

    fun <T : IChoose> pickRun(t: T, call: (T) -> Unit) {
        pick(t, true)
        call.invoke(t)
    }


}