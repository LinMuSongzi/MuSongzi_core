package com.musongzi.core.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.musongzi.core.ExtensionCoreMethod;
import com.musongzi.core.annotation.CollecttionsEngine;
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness;
import com.musongzi.core.base.manager.RetrofitManager;
import com.musongzi.core.base.vm.SaveStateHandleWarp;
import com.musongzi.core.itf.IAgentHolder;
import com.musongzi.core.itf.IClient;
import com.musongzi.core.itf.IViewInstance;
import com.musongzi.core.itf.IWant;
import com.musongzi.core.itf.holder.IHolderViewModel;
import com.musongzi.core.itf.page.IDataEngine;
import com.musongzi.core.itf.page.IRead;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.annotations.NonNull;

public class InjectionHelp {



    @org.jetbrains.annotations.NotNull
    public static final String BUSINESS_NAME_KEY = "BUSINESS_NAME_KEY";

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
            //???????????????????????????
            Type[] types = ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments();

            for (int i = 0; i < types.length; i++) {
                //??????????????????????????????
                if (actualTypeArgumentsIndex == i) {
                    //????????????class???class????????????Type??????
                    c = (Class) types[i];
                    break;
                }
            }
            try {
                //????????????Databinding???????????????inflate??????
                Method method = c.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                //?????????????????????c class ??????????????????????????????????????????????????????
                return (D) method.invoke(null, LayoutInflater.from(ActivityThreadHelp.getCurrentApplication()), parent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //??????????????????????????????????????????????????????????????????
            return findDataBinding(aClass.getSuperclass(), parent, name, actualTypeArgumentsIndex);
        }

    }


    @NotNull
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
    public static <A extends IViewInstance, B extends IAgentHolder<A>> B injectBusiness(@NotNull Class<B> targetClass, @NotNull A agent) {
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
     * @param activity         ?????????activity????????????fragment
     * @param defaultArgs      ??????????????????activity???defaultArgs ???activity??? savedInstanceState.
     *                         ??????????????????frament ???defaultArgs ???fragment???argment bundle
     * @param clazz
     * @param savedStateHandle
     * @param <C>
     * @param <V>
     * @return
     */
    @org.jetbrains.annotations.Nullable
    public static <C extends IClient, V extends ViewModel> V injectViewModel(@NonNull C activity, Bundle defaultArgs,
                                                                             @org.jetbrains.annotations.NonNls Class<V> clazz, SavedStateHandle savedStateHandle) {
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

                vmInstance.putArguments(defaultArgs);
                vmInstance.attachNow(activity);
                vmInstance.setHolderSavedStateHandle(new SaveStateHandleWarp(savedStateHandle));
                vmInstance.handlerArguments();
            }
            return vmI;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
//            if(clazz != ActivityHelpViewModel.class){
//              return   injectViewModel(activity, defaultArgs, ActivityHelpViewModel.class, savedStateHandle);
//            }
        }
    }

    public static ISupprotActivityBusiness injectActivityBusiness(ClassLoader classLoader, String name) {
        try {
            return (ISupprotActivityBusiness) classLoader.loadClass(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    public static Fragment injectFragment(@org.jetbrains.annotations.NotNull ClassLoader classLoader, @NotNull String className, @org.jetbrains.annotations.Nullable Bundle dataBundle) {
        try {
            Class c = classLoader.loadClass(className);
            return ExtensionCoreMethod.instance(c,dataBundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    public static <C> C checkClient(@org.jetbrains.annotations.Nullable C client, @NotNull Class<?> vm, @NotNull int index) {
        if(client == null){
            return null;
        }
        Class<?> aClass =  InjectionHelp.findGenericClass(vm,index);
        if(aClass.isInstance(client)){
            return client;
        }
        return null;
    }

    public static <Api> Api injectApi(@NotNull IWant apiViewModel, int indexApiActualTypeArgument) {
       return RetrofitManager.getInstance().getApi(InjectionHelp.findGenericClass(apiViewModel.getClass(), indexApiActualTypeArgument), apiViewModel);
    }

    @NotNull
    public static ClassLoader getClassLoader() {
        return InjectionHelp.class.getClassLoader();
    }
}
