package com.musongzi.spi

import com.musongzi.comment.ExtensionMethod.getSaveStateValue
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.itf.ISaveStateHandle
import com.musongzi.core.base.manager.ManagerInstanceHelp
import java.lang.ref.WeakReference

/*** created by linhui * on 2022/8/21
 *
 * 缓存注入服务管理者
 *
 * */
class SpiManager private constructor() : InstanceManager, IHolderSavedStateHandle {


    lateinit var mISaveStateHandle: ISaveStateHandle

    /**
     * 加载规则
     */
    private var rulre: IStrategyRule? = null


    private fun <T> load(
        request: ISpiRequest,
        s: HashMap<String, Pair<Class<*>, Class<*>>>? = null
    ): T? {
        var sets: HashMap<String, Pair<Class<*>, Class<*>>>?
        sets = s ?: CACHE_PARIE_KEY.getSaveStateValue(this)
        if (sets == null) {
            sets = HashMap()
            CACHE_PARIE_KEY.saveStateChange(this, sets)
        }
        return (sets[request.orderName()]?.let {
            request.letMeInstance(it.second) ?: it.second.newInstance()
        } ?: findLoad(request, sets)) as? T
    }

    private fun findLoad(
        request: ISpiRequest,
        sets: HashMap<String, Pair<Class<*>, Class<*>>>
    ): Any? {
        ruleCheck(request, sets)
        return load(request, sets)
    }

    private fun ruleCheck(
        request: ISpiRequest,
        sets: java.util.HashMap<String, Pair<Class<*>, Class<*>>>
    ) {
        val clazz = rulre?.onLoadRule(request)!!
        sets[request.orderName()] = request.getRequestLoaderClass() to clazz
    }

//    override fun managerId(): Int {
//        return hashCode()
//    }

    override fun onReady(a: Any?) {
        rulre = a as? IStrategyRule
        setHolderSavedStateHandle(LocalSavedHandler())
    }

    override fun getHolderSavedStateHandle(): ISaveStateHandle {
        return mISaveStateHandle
    }

    override fun setHolderSavedStateHandle(savedStateHandle: ISaveStateHandle) {
        this.mISaveStateHandle = savedStateHandle
    }


    internal class ManagerInstanceHelpImpl(private var rule: WeakReference<Class<*>>) : ManagerInstanceHelp {
        override fun instance(): InstanceManager? {
            if (MANAGER == null) {
                synchronized(SpiManager::class.java) {
                    if (MANAGER == null) {
                        return SpiManager().also { MANAGER = it }
                    }
                }
            }
            return MANAGER
        }

        override fun readyNow(my: InstanceManager): Any? {
            return rule.get()?.newInstance()
        }

        override fun key(): String {
            return SPI_MANAGER
        }

        override fun classLoadPathName(): String? {
            return null
        }
    }

    companion object {
        const val SPI_MANAGER = "SpiManager"
        const val CACHE_PARIE_KEY = "CACHE_PARIE_KEY"

        var MANAGER: SpiManager? = null

        /**
         * 加载需要的实体,基于[ISpiRequest.orderName].
         * 通过,[IStrategyRule.onLoadRule] 策略去实例化;需要提前配置好
         */
        fun <T> loadInstance(request: ISpiRequest): T? {
            return MANAGER?.load(request)
        }
    }
}