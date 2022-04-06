package com.musongzi.test.simple;

import android.widget.RelativeLayout;

import com.musongzi.core.util.ActivityThreadHelp;
import com.musongzi.test.Api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Duration;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String URL = "";

    static RetrofitManager MANAGER;
    private Api api;

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
    public static Api getApi() {
        Api api = RetrofitManager.getInstance().api;
        if (api == null) {
            api = RetrofitManager.getInstance().retrofit.create(Api.class);
            RetrofitManager.getInstance().api = api;
        }
        return api;
    }

}
