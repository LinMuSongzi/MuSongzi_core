package com.musongzi.core.itf

import com.musongzi.core.IVideoHandler
import java.lang.reflect.InvocationHandler

interface IEventManager :ISingleManager, InvocationHandler {
    fun put(name: String, h: () -> Any)
    fun remove(name: String, callBack: Any)
    fun call(name: String)
//    public Object invoke(Class, Method method, Object[] args)
}