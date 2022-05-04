package com.musongzi.core.base.manager

import android.util.Log
import com.musongzi.core.itf.IEventManager
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * 事件事务管理
 *
 * 调用 {interface.class}.event.{method}即可
 *
 *
 */
internal class EventManger : IEventManager {

    companion object {
        const val TAG = "EventManger"
    }

    var classMap = HashMap<Class<*>, Set<Any>>()
    var arrayProxyInstanceMap = HashMap<Class<*>, Any>()

    private val help: EventMangerHelp by lazy {
        EventMangerHelp(this@EventManger)
    }

    override fun <T> put(name: Class<T>, h: () -> T) {
        var c: LinkedHashSet<Any>? = classMap[name] as? LinkedHashSet<Any>
//        Log.i(TAG, ": put 0 ")
        if (c == null) {
//            Log.i(TAG, ": put 1 list null add ")
            c = LinkedHashSet()
            classMap[name] = c
        }

        c.add(h.invoke()!!)
//        Log.i(TAG, ": put 2 , size = " + c.size)
    }

    override fun <T> remove(name: Class<T>, callBack: T) {
        val c = classMap[name]
        c?.let {
            (it as LinkedHashSet).remove(callBack)
        }
    }

    override fun <T> asInterface(clazz: Class<T>): T? {
        return if (!clazz.isInterface) {
            null;
        } else {
            val api = arrayProxyInstanceMap
            var instance = api[clazz]
            if (instance == null) {
                instance = Proxy.newProxyInstance(javaClass.classLoader, arrayOf(clazz)) { proxy, method, args ->
                    asInvocationHandler().invoke(proxy, method, args)
                }
                api[clazz] = instance
            }
            instance as T
        }
    }

    fun asInvocationHandler() = help

    override fun onReady() {

    }
}