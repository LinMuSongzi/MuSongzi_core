package com.musongzi.core.base.manager

import android.content.Context
import android.util.Log
import com.musongzi.core.base.manager.event.EventMethodProxy
import com.musongzi.core.itf.IEventManager
import com.musongzi.core.util.ActivityThreadHelp
import dalvik.system.DexFile
import java.lang.Appendable
import java.lang.Exception
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
     * class<*> 当前的接口class <key>
     *     value{
     *       (     instance(Proxy) 代理接口的实例 <Pair:first>
     *            EventMethodProxy(InvocationHandler) 代理接口回调函数 <second>)<Pair:first>
     *       Ser<Any> callback 所有注册的基于接口的回调<pair:first>
     *     }
     *
     *
     */
    private var instancesByClassMap = HashMap<Class<*>, EventMethodProxy>()

    override fun <T> put(name: Class<T>, h: () -> T) {
        if (!name.isInterface) {
            throw Exception("必须是接口才可以！！！！！！！！！")
        }
        holderMap(name).add(h.invoke()!!)
    }

    override fun holderMap(name: Class<*>): HashSet<Any> {
        val mmap = instancesByClassMap
        var c: EventMethodProxy? = mmap[name]
        val set: Set<Any>
        if (c == null) {
            set = HashSet();
            val newMethod = EventMethodProxy(this, name, set);
//            newMethod.instance
            //Proxy.newProxyInstance(javaClass.classLoader, arrayOf(name), newMethod)
//            val p2 = instance to newMethod
//            c = newMethod to set
//            for (k in mmap.keys) {
//                if (name.isAssignableFrom(k)) {
//                    mmap[k]?.first?.second?.addParent(newMethod)
//                }
//                if (k.isAssignableFrom(name)) {
//                    mmap[k]?.first?.second?.addChild(newMethod)
//                }
//            }
            instancesByClassMap[name] = newMethod

        } else {
            set = c.instanceSet as HashSet<Any>
        }
        return set;
    }

    override fun <T> remove(name: Class<T>, callBack: T) {
        instancesByClassMap[name]?.apply {
            instanceSet.remove(callBack)
        }
    }

    override fun <T> asInterface(clazz: Class<T>): T? {
        return if (!clazz.isInterface) {
            null;
        } else {
            instancesByClassMap[clazz]?.instance as? T
        }
    }

    override fun onReady() {
    }


}