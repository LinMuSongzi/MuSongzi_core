package com.musongzi.core.util;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.musongzi.core.ExtensionCoreMethod;
import com.musongzi.core.annotation.CollecttionsEngine;
import com.musongzi.core.base.business.BaseOnClickAction;
import com.musongzi.core.base.business.itf.ILightWeightBus;
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness;
import com.musongzi.core.base.manager.RetrofitManager;
import com.musongzi.core.base.map.SaveStateHandleWarp;
import com.musongzi.core.itf.IAgentHolder;
import com.musongzi.core.itf.IBusiness;
import com.musongzi.core.itf.IClient;
import com.musongzi.core.itf.IOnClickAction;
import com.musongzi.core.itf.IViewInstance;
import com.musongzi.core.itf.IWant;
import com.musongzi.core.itf.holder.IHolderActivity;
import com.musongzi.core.itf.holder.IHolderViewModel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import kotlin.Deprecated;
import kotlin.jvm.functions.Function1;

@Deprecated(message = "将会被Kclass替代")
public class InjectionHelp {

    static Map<String, Class<?>> CACHE_CALSS = new HashMap<>();

    static Map<String, Method> CACHE_METHOD = new HashMap<>();

    @org.jetbrains.annotations.NotNull
    public static final String BUSINESS_NAME_KEY = "BUSINESS_NAME_KEY";
    private static final String TAG = "InjectionHelp";
    public static final ClassLoader CLASS_LOADER = InjectionHelp.class.getClassLoader();

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


    public static <D extends ViewDataBinding> D findDataBinding(Class<?> aClass, ViewGroup parent, String name, int actualTypeArgumentsIndex) {
        return findDataBinding(aClass, parent, name, actualTypeArgumentsIndex, null);
    }

    @Nullable
    public static <D extends ViewDataBinding> D findDataBinding(Class<?> aClass, ViewGroup parent, String name, int actualTypeArgumentsIndex,
                                                                @Nullable DataBindingComponent dataBindingComponent) {

        Function1<Method, D> function1 = method -> {
            try {
                return (D) method.invoke(null, LayoutInflater.from(ActivityThreadHelp.getCurrentApplication()), parent, false, dataBindingComponent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };

        Method method = CACHE_METHOD.get(aClass.getCanonicalName() + actualTypeArgumentsIndex);
        if (method != null) {
            Log.i(TAG, "findDataBinding: had cache " + aClass.getCanonicalName());
            return function1.invoke(method);
        }
        Log.i(TAG, "findDataBinding:no had cache , ready find " + aClass.getCanonicalName());
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
                method = c.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class, Object.class);
                CACHE_METHOD.put(aClass.getCanonicalName() + actualTypeArgumentsIndex, method);
                //根据获取到泛型c class ，调用函数方法初始化指定泛型并且返回
                return function1.invoke(method);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //如果父类不符合名字要求，则递归往上层继续寻找
            return findDataBinding(aClass.getSuperclass(), parent, name, actualTypeArgumentsIndex);
        }

    }


    @NotNull
    public static <T> Class<T> findGenericClass(@NotNull Class<?> topClass, int actualTypeArgumentsViewModelIndex) {
        Class<T> cache = findCacheClass(topClass, actualTypeArgumentsViewModelIndex);
        if (cache == null) {
            Function1<Class, Class> function0 = new Function1<Class, Class>() {
                @Override
                public Class invoke(Class aClass) {
                    Type type = aClass.getGenericSuperclass();
                    if (type instanceof ParameterizedType) {
                        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                        if (types.length > actualTypeArgumentsViewModelIndex) {
                            Class<T> findClass = (Class<T>) types[actualTypeArgumentsViewModelIndex];
                            cacheClass(topClass, findClass, actualTypeArgumentsViewModelIndex);
                            return findClass;
                        }
                    }
                    return invoke(aClass.getSuperclass());
                }
            };
            return function0.invoke(topClass);
        } else {
            return cache;
        }
    }

    private static <T> void cacheClass(Class<?> c, Class<T> findClass, int actualTypeArgumentsViewModelIndex) {
        String k = c.getCanonicalName() + actualTypeArgumentsViewModelIndex;
        Log.i(TAG, "cacheClass: key = " + k + " , findClass = " + findClass);
        CACHE_CALSS.put(k, findClass);
    }

    private static <T> Class<T> findCacheClass(Class<?> aClass, int actualTypeArgumentsViewModelIndex) {
        String k = aClass.getCanonicalName() + actualTypeArgumentsViewModelIndex;
        Class<T> cache = (Class<T>) CACHE_CALSS.get(aClass.getCanonicalName() + actualTypeArgumentsViewModelIndex);
        Log.i(TAG, "findCacheClass: key = " + k + " , find cache = " + cache);
        return cache;
    }

    @org.jetbrains.annotations.Nullable
    public static <V> V findViewModel(@NotNull Class<?> javaClass, String name, @NotNull ViewModelProvider viewModelProvider, int actualTypeArgumentsViewModelIndex, Class[] findClass) {
        return findViewModel(javaClass, name, viewModelProvider, actualTypeArgumentsViewModelIndex, findClass, 0);
    }

    @org.jetbrains.annotations.Nullable
    public static <V> V findViewModel(@NotNull Class<?> javaClass, String name, @NotNull ViewModelProvider viewModelProvider, int actualTypeArgumentsViewModelIndex, Class[] findClass, int check) {
//        int checkNow = check;
        Class c;
//        if (check == 0) {
//            c = CACHE_CALSS.get(javaClass.getCanonicalName() + name + actualTypeArgumentsViewModelIndex);
//            if(c != null){
//
//            }
//        }
        if (javaClass.getSuperclass().getName().equals(name)) {
            Type type = javaClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types.length > actualTypeArgumentsViewModelIndex) {
                    c =(Class) types[actualTypeArgumentsViewModelIndex];
                    findClass[0] = c;
                    return (V) viewModelProvider.get(c);
                }
            }
        }
        return findViewModel(javaClass.getSuperclass(), name, viewModelProvider, actualTypeArgumentsViewModelIndex, findClass, check);
    }

    public static <V extends ViewModel> V getViewModel(@NotNull ViewModelProvider provider, @org.jetbrains.annotations.Nullable Class clazz) {
        return (V) provider.get(clazz);
    }

    @org.jetbrains.annotations.Nullable
    public static <A extends IViewInstance, B extends IBusiness> B injectBusiness(@NotNull Class<B> targetClass, A agent) {
        return injectBusiness(targetClass, agent, null);
    }

    @org.jetbrains.annotations.Nullable
    public static <A extends IViewInstance, B extends IBusiness> B injectBusiness(@NotNull Class<B> targetClass,
                                                                                  @NotNull A agent, @Nullable IBusiness businessWrapInstance) {
        try {
            Constructor<B> constructor;
            if (businessWrapInstance == null) {
                constructor = targetClass.getConstructor();
            } else {
                constructor = targetClass.getConstructor(IBusiness.class);
            }
            return injectBusiness(constructor, agent, businessWrapInstance);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    @org.jetbrains.annotations.Nullable
    private static <A extends IViewInstance, B extends IBusiness> B injectBusiness(@NotNull Constructor<B> constructor, @NotNull A agent, @Nullable IBusiness businessWrapInstance) {
        B instance = null;
        try {
            if (businessWrapInstance == null) {
                instance = constructor.newInstance();
            } else {
                instance = constructor.newInstance(businessWrapInstance);
            }
            if (instance instanceof IAgentHolder) {
                try {
                    ((IAgentHolder) instance).setAgentModel(agent);
                } catch (Exception ex) {
                    Log.i(TAG, "injectBusiness: setAgent error in instance");
                    ex.printStackTrace();
                }
            }
            instance.afterHandlerBusiness();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }


    /**
     * @param activity         或许是activity，或许是fragment
     * @param defaultArgs      如果调用者是activity，defaultArgs 是activity的 savedInstanceState.
     *                         如果调用者是frament ，defaultArgs 是fragment的argment bundle
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
            return ExtensionCoreMethod.instance(c, dataBundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    public static <F extends Fragment> Fragment injectFragment(@NotNull Class<F> clazz, @org.jetbrains.annotations.Nullable Bundle dataBundle) {
        try {
            return ExtensionCoreMethod.instance(clazz, dataBundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    public static <C> C checkClient(@org.jetbrains.annotations.Nullable C client, @NotNull Class<?> vm, @NotNull int index) {
        if (client == null) {
            return null;
        }
        Class<?> aClass = InjectionHelp.findGenericClass(vm, index);
        if (aClass.isInstance(client)) {
            return client;
        }
        return null;
    }

    public static <Api> Api injectApi(@NotNull IWant apiViewModel, int indexApiActualTypeArgument) {
        return RetrofitManager.getInstance().getApi(InjectionHelp.findGenericClass(apiViewModel.getClass(), indexApiActualTypeArgument), apiViewModel);
    }

    @NotNull
    public static ClassLoader getClassLoader() {
        if (CLASS_LOADER != null) {
            return CLASS_LOADER;
        } else {
            return findLoader();
        }
    }

    private static ClassLoader findLoader() {
        return InjectionHelp.class.getClassLoader();
    }


    @org.jetbrains.annotations.Nullable
    public static View.OnClickListener injectOnClick(@NotNull String click, @org.jetbrains.annotations.Nullable String action) {
        try {
            Class<?> clazz = ActivityThreadHelp.getCurrentApplication().getClassLoader().loadClass(click);
            if (clazz.isAssignableFrom(BaseOnClickAction.class)) {
                return (IOnClickAction) clazz.getConstructor(Integer.class).newInstance(action);
            } else {
                throw new IllegalArgumentException("不是 BaseOnClickAction 类型");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    public static IHolderActivity buildHolderActivityProxy(@org.jetbrains.annotations.Nullable IHolderActivity instance) {


        return null;
    }
}
