package com.musongzi.core.base.manager.event;

import androidx.annotation.NonNull;

import com.musongzi.core.base.manager.EventManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import kotlin.Pair;
import kotlin.TuplesKt;

public class EventMethodProxy implements InvocationHandler {

    Class<?> aClass;
    EventManager eventManager;

    public EventMethodProxy(EventManager eventManager, Class<?> c) {
        this.eventManager = eventManager;
        this.aClass = c;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set classSetMap = eventManager.getInstancesByClassMap().get(aClass).getSecond();
        if (classSetMap.isEmpty()) {
            return null;
        }
        for (Object instance : classSetMap) {
            method.invoke(instance, args);
        }
        return null;
    }
}
