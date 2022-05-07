package com.musongzi.core.base.manager.event;

import android.util.Log;

import com.musongzi.core.base.manager.EventManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.Pair;

public class EventMethodProxy implements InvocationHandler {

    private static final String TAG = "EventMethodProxy";
    Class<?> aClass;
    final List<Method> methods = new ArrayList<Method>();
    final List<Class<?>> interfaces = new ArrayList<>();
    final List<EventMethodProxy> parentAdd = new ArrayList<>();
    Set<Object> instanceSet;
    EventManager eventManager;


    public EventMethodProxy(EventManager eventManager, Class<?> c, HashSet<Object> set) {
        this.eventManager = eventManager;
        this.aClass = c;
        this.instanceSet = set;
        methods.addAll(Arrays.asList(c.getDeclaredMethods()));
        interfaces.addAll(Arrays.asList(aClass.getInterfaces()));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        Set<?> classSetMap = instanceSet;
        if (classSetMap.isEmpty()) {
            return null;
        }
        Log.i(TAG, "invoke: " + method);
        try {
            findParentMethod(method, args);
            for (Object instance : classSetMap) {
                method.invoke(instance, args);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void findParentMethod(Method method, Object[] args) {
        boolean parentFlag = methods.indexOf(method) < 0;
        if (parentFlag) {
            for (EventMethodProxy cc : parentAdd) {
                cc.invoke(null, method, args);
            }
        }
    }

    public void addParent(@NotNull EventMethodProxy k) {
        Log.i(TAG, "addParent: 有父类注册进来了");
        Log.i(TAG, "addParent: 自己是：" + aClass.getCanonicalName() + " , 父类是：" + k.aClass.getCanonicalName());
        parentAdd.add(k);
    }
}
