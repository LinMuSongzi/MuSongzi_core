package com.musongzi.test

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.musongzi.core.base.manager.RetrofitManager
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate
import java.util.*
import kotlin.collections.ArrayList

class MyApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    companion object {
        const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        Thread.UncaughtExceptionHandler { t, e ->
            Log.i(TAG, "onCreate: " + e.message + " , " + Arrays.toString(e.stackTrace) + "\n");
        }
        RetrofitManager.getInstance().setCallBack { proxy, method, args ->
            var cb: Any? = null
            if (method.name == "getArrayEngine") {
                if ((args[0] as Int) > 0) {
                    cb = ObservableCreate.fromArray(ArrayList<String>())
                } else {
                    cb = ObservableCreate.fromArray(
                        arrayOf(
                            "a",
                            "1",
                            "2",
                            "3",
                            "4",
                            "5",
                            "6",
                            "ad",
                            "1",
                            "2",
                            "3",
                            "4",
                            "5",
                            "6",
                            "ad"
                        )
                    )
                }
            }
            cb
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated: ")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, "onActivityStarted: ")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "onActivityResumed: ")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG, "onActivityPaused: ")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG, "onActivityStopped: ")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i(TAG, "onActivitySaveInstanceState: ")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG, "onActivityDestroyed: ")
    }
}