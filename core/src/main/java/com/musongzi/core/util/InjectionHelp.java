package com.musongzi.core.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.musongzi.core.annotation.CollecttionsEngine;
import com.musongzi.core.base.vm.ActivityHelpViewModel;
import com.musongzi.core.base.vm.SaveStateHandleWarp;
import com.musongzi.core.itf.IAgentHolder;
import com.musongzi.core.itf.IClient;
import com.musongzi.core.itf.holder.IHolderActivity;
import com.musongzi.core.itf.holder.IHolderLifecycle;
import com.musongzi.core.itf.holder.IHolderViewModel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;

public class InjectionHelp {


    public static CollecttionsEngine findAnnotation(Class<?> thisClazz) {
//        if(thisClazz.getName().equals("java.lang.Object")){
//            return null;
//        }
        CollecttionsEngine collecttionsEngine = thisClazz.getAnnotation(CollecttionsEngine.class);
        if (collecttionsEngine == null) {
            try {
                return findAnnotation(thisClazz.getSuperclass());
            } catch (Exception ex) {
                return null;
            }
        } else {
            return collecttionsEngine;
        }
    }

    @Nullable
    public static <D extends ViewDataBinding> D findDataBinding(Class<?> aClass, ViewGroup parent, String name, int actualTypeArgumentsIndex) {

//        Log.i(TAG, "getDataBinding: " + aClass.getSuperclass().getName() + " , " + name);
        if (aClass.getSuperclass() != null && name.equals(aClass.getSuperclass().getName())) {//aClass.getSuperclass().getName().equals(name)) {
            Class<?> c = null;
            //获取所有父类的泛型
            Type[] types = ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments();

            for (int i = 0; i < types.length; i++) {
                //判断指定下标是否一致
                if (actualTypeArgumentsIndex == i) {
                    //一致转成class，class默认继承Type接口
                    c = (Class) types[i];
                    break;
                }
            }
            try {
                //反射获取Databinding通用的静态inflate方法
                Method method = c.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                //根据获取到泛型c class ，调用函数方法初始化指定泛型并且返回
                return (D) method.invoke(null, LayoutInflater.from(ActivityThreadHelp.getCurrentApplication()), parent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //如果父类不符合名字要求，则递归往上层继续寻找
            return findDataBinding(aClass.getSuperclass(), parent, name, actualTypeArgumentsIndex);
        }

    }


    public static <T> Class<T> findGenericClass(@NotNull Class<?> aClass, int actualTypeArgumentsViewModelIndex) {
//        Class<T> cache = findCache();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length > actualTypeArgumentsViewModelIndex) {
                return (Class<T>) types[actualTypeArgumentsViewModelIndex];
            }
        }
        return findGenericClass(aClass.getSuperclass(), actualTypeArgumentsViewModelIndex);
    }

    @org.jetbrains.annotations.Nullable
    public static <V> V findViewModel(@NotNull Class<?> javaClass, String name, ViewModelProvider viewModelProvider, int actualTypeArgumentsViewModelIndex, Class[] findClass) {
        if (javaClass.getSuperclass().getName().equals(name)) {
            Type type = javaClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types.length > actualTypeArgumentsViewModelIndex) {
                    Class c = (Class) types[actualTypeArgumentsViewModelIndex];
                    findClass[0] = c;
                    return (V) viewModelProvider.get(c);
                }
            }
        }
        return findViewModel(javaClass.getSuperclass(), name, viewModelProvider, actualTypeArgumentsViewModelIndex, findClass);
    }

    public static <V> V getViewModel(@NotNull ViewModelProvider provider, @org.jetbrains.annotations.Nullable Class clazz) {
        return (V) provider.get(clazz);
    }



    @org.jetbrains.annotations.Nullable
    public static <A extends IHolderLifecycle, B extends IAgentHolder<A>> B injectBusiness(@NotNull Class<B> targetClass, @NotNull A agent) {
        B instance = null;
        try {
            instance = targetClass.newInstance();
            instance.setAgentModel(agent);
            instance.afterHandlerBusiness();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     *
     * @param activity 或许是activity，或许是fragment
     * @param defaultArgs 如果调用者是activity，defaultArgs 是activity的 savedInstanceState.
     *                    如果调用者是frament ，defaultArgs 是fragment的argment bundle
     * @param clazz
     * @param savedStateHandle
     * @param <C>
     * @param <V>
     * @return
     */
    @org.jetbrains.annotations.Nullable
    public static <C extends IClient,V extends ViewModel> V injectViewModel(@NonNull C activity, Bundle defaultArgs,
                                                           @org.jetbrains.annotations.Nullable Class<V> clazz,SavedStateHandle savedStateHandle) {
        try {
            V vmI = clazz.newInstance();

            if (vmI instanceof IHolderViewModel) {
                IHolderViewModel vmInstance = (IHolderViewModel) vmI;
                /**
                 *       Log.i(TAG, "createViewModel: $vm")
                 * //        val vm = superV as? IHolderViewModel<*, *>
                 *         vm?.let {
                 *             it.attachNow(this)
                 *             it.putArguments(arguments)
                 *             it.handlerArguments()
                 *         }
                 */
                vmInstance.attachNow(activity);
                vmInstance.setHolderSavedStateHandle(new SaveStateHandleWarp(savedStateHandle));
                vmInstance.putArguments(defaultArgs);
                vmInstance.handlerArguments();
            }
            return vmI;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
//            if(clazz != ActivityHelpViewModel.class){
//              return   injectViewModel(activity, defaultArgs, ActivityHelpViewModel.class, savedStateHandle);
//            }
        }
    }
}
