package com.musongzi.test

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.musongzi.core.ExtensionMethod.bean
import com.musongzi.core.base.manager.RetrofitManager
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.ArrayList

class MyApplication : MultiDexApplication() {
    companion object {
        const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
        Thread.UncaughtExceptionHandler { t, e ->
            Log.i(TAG, "onCreate: " + e.message + " , " + Arrays.toString(e.stackTrace) + "\n");
        }
        RetrofitManager.getInstance().init(object : RetrofitManager.CallBack {
            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any? {
                var cb: Any? = null
                if (method.name == "getArrayEngine") {
                    if ((args[0] as Int) > 1) {
                        cb = ObservableCreate.fromArray(emptyArray<String>())
                    } else {
                        cb = ObservableCreate.fromArray(
                            arrayOf(
                                "a".bean(),
                                "1".bean(),
                                "2".bean(),
                                "3".bean(),
                                "4".bean(),
                                "5".bean(),
                                "6".bean(),
                                "ad".bean(),
                                "1".bean(),
                                "2".bean(),
                                "3".bean(),
                                "4".bean(),
                                "5".bean(),
                                "6".bean(),
                                "ad".bean()
                            )

                        )
                    }
                }
                return cb
            }

            override fun getOkHttpCLient(): OkHttpClient? = null

            override fun getRetrofit(): Retrofit? = null

        })
    }
}