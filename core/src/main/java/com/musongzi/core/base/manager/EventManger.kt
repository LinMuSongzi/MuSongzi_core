package com.musongzi.core.base.manager

import com.musongzi.core.IVideoHandler
import com.musongzi.core.itf.IEventManager
import java.lang.reflect.Method

internal class EventManger : IEventManager {


    var classMap = HashMap<Class<*>, List<Any>>()


    override fun put(name: String, h: () -> Any) {
        TODO("Not yet implemented")
    }


    override fun remove(name: String, callBack: Any) {
        TODO("Not yet implemented")
    }

    override fun call(name: String) {
        TODO("Not yet implemented")
    }


    override fun onReady() {
        TODO("Not yet implemented")
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        for (v in classMap.keys) {
            if (v.isInstance(proxy)) {
                for (instance in classMap[v]!!) {
                   return method?.invoke(instance,args)
                }
                break
            }
        }
        return null
    }

}