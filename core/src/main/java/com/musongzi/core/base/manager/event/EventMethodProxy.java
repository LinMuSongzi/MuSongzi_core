package com.musongzi.core.base.manager.event;

import android.util.Log;

import com.musongzi.core.base.manager.EventManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventMethodProxy implements InvocationHandler {

    private static final String TAG = "EventMethodProxy";
    /**
     * 当前回调接口 class
     */
    Class<?> aClass;
    final List<Method> methods = new ArrayList<Method>();
//    final List<Class<?>> interfaces = new ArrayList<>();
//    final List<EventMethodProxy> parents = new ArrayList<>();
//    final List<EventMethodProxy> childs = new ArrayList<>();
    Set<Object> instanceSet;
    EventManager eventManager;
    private Object instance;

    public EventMethodProxy(EventManager eventManager, Class<?> c, HashSet<Object> set) {
        this.eventManager = eventManager;
        this.aClass = c;
        this.instanceSet = set;
        methods.addAll(Arrays.asList(c.getDeclaredMethods()));
//        interfaces.addAll(Arrays.asList(aClass.getInterfaces()));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        Set<?> classSetMap = instanceSet;
        if (classSetMap.isEmpty()) {
            return null;
        }
        Log.i(TAG, "invoke: " + method);
        try {
            Set<Object> ss = instanceSet;//findParentMethod(this, method, args);
            for (Object instance : ss) {
                method.invoke(instance, args);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    private static Set<Object> findParentMethod(EventMethodProxy eventMethodProxy, Method method, Object[] args) throws Exception {
//        boolean isMyFlag = eventMethodProxy.methods.indexOf(method) >= 0;
//        if (isMyFlag) {
//            if (eventMethodProxy.childs.isEmpty()) {
//                return eventMethodProxy.instanceSet;
//            } else {
//                return findChildMethod(eventMethodProxy, method, args);
//            }
//        } else {
//            if (eventMethodProxy.parents.isEmpty()) {
//                throw new Exception("错误！");
//            } else {
//                Set<Object> set = new HashSet<>();
//                for (EventMethodProxy ep : eventMethodProxy.parents) {
//                    set.addAll(findParentMethod(ep, method, args));
//                }
//                return set;
//            }
//        }
//    }

//    private static Set<Object> findChildMethod(EventMethodProxy eventMethodProxy, Method method, Object[] args) throws Exception {
//        Set<Object> set = new HashSet<>();
//        for (EventMethodProxy child : eventMethodProxy.childs) {
//            if (!child.instanceSet.isEmpty()) {
//                set.addAll(child.instanceSet);
//            }
//            if (!child.childs.isEmpty()) {
//                set.addAll(findParentMethod(child, method, args));
//            }
//        }
//        return set;
//    }

//    public void addParent(@NotNull EventMethodProxy parent) {
//        Log.i(TAG, "addParent: 有父类注册进来了");
//        Log.i(TAG, "addParent: 自己是：" + aClass.getCanonicalName() + " , 父类是：" + parent.aClass.getCanonicalName());
//        parents.add(parent);
//    }
//
//    public void addChild(@NotNull EventMethodProxy child) {
//        Log.i(TAG, "addParent: 有子类类注册进来了");
//        Log.i(TAG, "addParent: 自己是：" + aClass.getCanonicalName() + " , 子类类是：" + child.aClass.getCanonicalName());
//        childs.add(child);
//    }


    public Set<Object> getInstanceSet() {
        return instanceSet;
    }

    @NotNull
    public Object getInstance() {
        if(instance == null) {
            instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{aClass}, this);
        }
        return instance;
    }
}
