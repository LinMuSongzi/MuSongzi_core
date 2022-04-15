package com.musongzi.core.base.manager

import android.util.Log
import com.musongzi.core.itf.IEventManager
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

internal class EventManger : IEventManager {

    companion object {
        const val TAG = "EventManger"
    }

    var classMap = HashMap<Class<*>, Set<Any>>()

    val help: EventMangerHelp by lazy {
        EventMangerHelp(this@EventManger)
    }

    override fun <T> put(name: Class<T>, h: () -> T) {
        var c: LinkedHashSet<Any>? = classMap[name] as? LinkedHashSet<Any>
        Log.i(TAG, ": put 0 ")
        if (c == null) {
            Log.i(TAG, ": put 1 list null add ")
            c = LinkedHashSet()
            classMap[name] = c
        }

        c.add(h.invoke()!!)
        Log.i(TAG, ": put 2 , size = " + c.size)
    }

    override fun <T> remove(name: Class<T>, callBack: T) {
        val c = classMap[name]
        c?.let {
            Log.i(TAG, ": remove 1 ,size = " + it.size)
            (it as LinkedHashSet).remove(callBack)
        }
        Log.i(TAG, ": remove 2 ,size = " + c?.size)
    }

    override fun onReady() {

    }
}