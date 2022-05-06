package com.musongzi.core.base.manager

import com.musongzi.core.base.manager.event.EventMethodProxy
import com.musongzi.core.itf.IEventManager
import java.lang.reflect.Proxy

/**
 * 事件事务管理
 *
 * 调用 {interface.class}.event.{method}即可
 *
 *
 */
internal class EventManager : IEventManager {

    companion object {
        const val TAG = "EventManger"
    }

    /**
     * instancesByClassMap 结构分析
     * class<*> 当前的接口class
     *     instance(Proxy) 代理接口的实例
     *          EventMethodProxy(InvocationHandler) 代理接口回调函数
     *     Ser<Any> callback 所有注册的基于接口的回调
     *
     *
     */
    var instancesByClassMap = HashMap<Class<*>, Pair<Pair<*,*>?, Set<Any>>?>()

    override fun <T> put(name: Class<T>, h: () -> T) {
        var c: Pair<Pair<*,*>?, Set<Any>>? = instancesByClassMap[name]
        val set: Set<Any>
        if (c == null) {
            set = HashSet();
            val newMethod = EventMethodProxy(this, name);
            val instance = Proxy.newProxyInstance(javaClass.classLoader, arrayOf(name), newMethod)
            val p2 = instance to newMethod
            c = p2 to set
            instancesByClassMap[name] = c
        } else {
            set = c.second as HashSet<Any>
        }
        set.add(h.invoke()!!)
    }

    override fun <T> remove(name: Class<T>, callBack: T) {
        val c = instancesByClassMap[name]
        c?.let {
            (it.second as HashSet).remove(callBack)
        }
    }

    override fun <T> asInterface(clazz: Class<T>): T? {
        return if (!clazz.isInterface) {
            null;
        } else {
            instancesByClassMap[clazz]?.first?.first as? T
        }
    }

//    fun asInvocationHandler() = help

    override fun onReady() {

    }
}