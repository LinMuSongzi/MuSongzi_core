package com.musongzi.core.base.business

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IAgentHolder
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

open class BaseLifeBusiness<L : IHolderLifecycle> : BaseMapBusiness<L>(), DefaultLifecycleObserver{

    override fun afterHandlerBusiness() {
        Log.i(TAG, "onCreateView:afterHandlerBusiness; -- ")
    }

    override fun setAgentModel(v: L) {
        super.setAgentModel(v)
        v.getThisLifecycle()?.lifecycle?.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        if (isEnableEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (isEnableEventBus()) {
            EventBus.getDefault().unregister(this)
            iAgent.getThisLifecycle()?.lifecycle?.removeObserver(this)
        }
    }

    protected open fun isEnableEventBus() = false

}
