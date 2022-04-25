package com.musongzi.core.base.manager;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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
//                    Log.i("eventFind", ": " + method.getName() + " , " + instance);
                    method.invoke(instance, args);
                }
            }
        }
        return null;
    }
}
