package com.musongzi.core.base.manager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.musongzi.core.itf.IWant;
import com.musongzi.core.util.ActivityThreadHelp;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String URL = "http://test.musiccz.net:6060/";

    static RetrofitManager MANAGER;
    private Map<String, Object> apis = new HashMap<>();

    static {
        MANAGER = new RetrofitManager();
    }

    private final Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private RetrofitManager() {

        retrofit = new Retrofit.Builder().baseUrl(URL)
                .client(getOkHttpCLient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();


    }

    private OkHttpClient getOkHttpCLient() {
        Cache cache = new Cache(ActivityThreadHelp.getCurrentApplication().getCacheDir(), 1024 * 1024 * 200);
        okHttpClient = new OkHttpClient().newBuilder().cache(cache).build();

        return okHttpClient;
    }


    public static RetrofitManager getInstance() {
        return MANAGER;
    }

    @NotNull
    public static <T> T getApi(Class<T> tClass) {
        return getApi(tClass, tClass.getName() + "_main", null);
    }

    @NotNull
    public static <T> T getApi(Class<T> tClass, IWant want) {
        return getApi(tClass, tClass.getName(), want);
    }

    @NotNull
    public static <T> T getApi(Class<T> tClass, String key, IWant want) {
        Map<String, Object> apis = RetrofitManager.getInstance().apis;
        if (apis.get(key) == null) {
            T t = RetrofitManager.getInstance().retrofit.create(tClass);
            apis.put(key, t);
        }
        if (want != null && want.getThisLifecycle() != null) {
            Proxy.newProxyInstance(tClass.getClassLoader(), tClass.getInterfaces(), (proxy, method, args) -> {
                Object returnInstance =  method.invoke(getApi(tClass),args);
                if(method.getReturnType().isAssignableFrom(Observable.class)){
                    ((Observable<?>)returnInstance).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(want.bindToLifecycle());
                }
                return returnInstance;
            });
            want.getThisLifecycle().getLifecycle().addObserver(new DefaultLifecycleObserver() {
                @Override
                public void onCreate(@NonNull LifecycleOwner owner) {

                }

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    RetrofitManager.getInstance().apis.remove(key);
                }
            });
        }
        return (T) apis.get(tClass.getName());
    }

}
