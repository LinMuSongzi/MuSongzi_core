package com.musongzi.core.base.business

import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.vm.IHandlerChooseViewModel

class HandlerChooseBusiness() : BaseLifeBusiness<IHandlerChooseViewModel<*>>() {


    constructor(v: IHandlerChooseViewModel<*>) : this() {
        setAgentModel(v)
        afterHandlerBusiness()
    }

    var pickData: BaseChooseBean? = null

    private fun pick(t: BaseChooseBean?, isPick: Boolean) = let {
        if (t != null) {
            t.choose(isPick)
            if (isPick) {
                pickData = t;
            }
        }
        it
    }

    fun pickRun(t: BaseChooseBean?) = pick(pickData, t)

    fun pick(unPick: BaseChooseBean?, tPick: BaseChooseBean?) = let {
        it.pick(unPick, false).pick(tPick, true).iAgent.updateByPick()
        it
    }

    fun <T : BaseChooseBean> pickRun(t: T, call: (T) -> Unit) {
        pick(t, true)
        call.invoke(t)
    }


}