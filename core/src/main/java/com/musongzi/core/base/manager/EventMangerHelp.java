package com.musongzi.core.base.manager;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class EventMangerHelp implements InvocationHandler {

    private EventManger eventManger;

    public EventMangerHelp(EventManger eventManger) {
        this.eventManger = eventManger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (Class<?> c : eventManger.getClassMap().keySet()) {
            if (c.isInstance(proxy)) {
                for (Object instance : eventManger.getClassMap().get(c)) {
                    method.invoke(instance, args);
//                    Log.i(EventManger.TAG, "invoke: class = " + c.getName() + " , method = " + method.getName());
                }
            }
        }
        return null;
    }
}
