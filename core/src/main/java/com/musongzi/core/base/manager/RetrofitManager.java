package com.musongzi.core.base.manager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.musongzi.core.itf.IWant;
import com.musongzi.core.util.ActivityThreadHelp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String URL = "http://shange.musiccz.net:6060/";
    private static final String URL2 = "http://192.168.1.106:8080/";

    static RetrofitManager MANAGER;
    private Map<String, Object> apis = new HashMap<>();

    static {
        MANAGER = new RetrofitManager();
    }

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private CallBack mCallBack;


    private RetrofitManager() {
    }

    public void init(CallBack callBack) {
        if (retrofit != null) {
            return;
        }
        setCallBack(callBack);
        if (callBack != null && callBack.getRetrofit() != null) {
            retrofit = callBack.getRetrofit();
        } else {
            retrofit = new Retrofit.Builder().baseUrl(URL2)
                    .client(getOkHttpCLient())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    private OkHttpClient getOkHttpCLient() {
        if (mCallBack != null) {
            okHttpClient = mCallBack.getOkHttpCLient();
        }
        if (okHttpClient == null) {
            Cache cache = new Cache(ActivityThreadHelp.getCurrentApplication().getCacheDir(), 1024 * 1024 * 200);
            okHttpClient = new OkHttpClient().newBuilder()
            //添加日志拦截器
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        }
        return okHttpClient;
    }


    public static RetrofitManager getInstance() {
        return MANAGER;
    }

    @NotNull
    public <T> T getApi(Class<T> tClass) {
        return getApi(tClass, tClass.getName() + "_main", null);
    }

    @NotNull
    public <T> T getApi(Class<T> tClass, IWant want) {
        if (want != null) {
            String key = tClass.getName() + want.hashCode();
            return getApi(tClass, key, want);
        }
        return getApi(tClass, tClass.getName(), null);
    }

    @NotNull
    public <T> T getApi(Class<T> tClass, String key, IWant want) {
        Map<String, Object> apis = RetrofitManager.getInstance().apis;
        T t = (T) apis.get(key);


        if (t == null) {
            if (want != null && want.getThisLifecycle() != null) {
                final WeakReference<CallBack> c = new WeakReference<>(mCallBack);
                final WeakReference<RetrofitManager> r = new WeakReference<>(MANAGER);
                InvocationHandler invocationHandler = new InvocationHandler() {
                    private final Object[] emptyArgs = new Object[0];

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        args = args != null ? args : emptyArgs;
                        Log.i(TAG, "invoke: 1");
                        Object returnInstance = null;
                        if (c.get() != null) {
                            Log.i(TAG, "invoke: 2");
                            returnInstance = c.get().invoke(proxy, method, args);
                        }
                        if (returnInstance == null) {
                            Log.i(TAG, "invoke: 3");
                            returnInstance = method.invoke(r.get().getApi(tClass), args);
                        }
                        Log.i(TAG, "invoke: 4");
                        if (method.getReturnType().isAssignableFrom(Observable.class)) {
                            Log.i(TAG, "invoke: 5");
                            returnInstance = ((Observable<?>) returnInstance).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(want.bindToLifecycle());
                        }
                        Log.i(TAG, "invoke: 6 "+returnInstance);
                        return returnInstance;
                    }
                };

                t = (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, invocationHandler);
                want.getThisLifecycle().getLifecycle().addObserver(new DefaultLifecycleObserver() {
                    @Override
                    public void onDestroy(@NonNull LifecycleOwner owner) {
                        Object flag = RetrofitManager.getInstance().apis.remove(key);
                        Log.i("Observable_Sub", "onDestroy: api " + flag + " , key = " + key);
                    }
                });
            } else {
                t = RetrofitManager.getInstance().retrofit.create(tClass);
            }
            apis.put(key, t);
        }
//        t = (T) apis.get(key);
        Log.i("Observable_Sub", "getApi: key = " + key);
        return t;
    }


    public interface CallBack extends InvocationHandler {
        @Nullable
        OkHttpClient getOkHttpCLient();

        @Nullable
        Retrofit getRetrofit();
    }

    static final String TAG = "InvocationHandler";
}

