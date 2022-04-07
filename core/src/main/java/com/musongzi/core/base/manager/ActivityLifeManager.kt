package com.musongzi.core.base.manager

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.musongzi.core.util.ActivityThreadHelp

class ActivityLifeManager private constructor() : ComponentCallbacks,
    Application.ActivityLifecycleCallbacks {

    companion object {
        private val MANAGER = ActivityLifeManager()
        private const val TAG = "ActivityLifeManager"

        @JvmStatic
        fun getInstance() = MANAGER

        @JvmStatic
        fun printActivity(activity: Activity): String {
            return "hashCode: ${activity.hashCode()} , name = " + activity.javaClass.name
        }
    }

    init {
        ActivityThreadHelp.getCurrentApplication().let {
            it.registerComponentCallbacks(this)
            (it as Application).registerActivityLifecycleCallbacks(this)
        }
    }

    private val lifeActivityList = ArrayList<Activity>()


    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i(TAG, "onConfigurationChanged: $newConfig")
    }

    override fun onLowMemory() {
        Log.i(TAG, "onLowMemory: ")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated: " + printActivity(activity))
        lifeActivityList.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, "onActivityStarted: " + printActivity(activity))
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "onActivityResumed: " + printActivity(activity))
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG, "onActivityPaused: " + printActivity(activity))
        if (activity.isFinishing) {
            lifeActivityList.remove(activity).apply {
                if (this) {
                    Log.i(TAG, "onActivityPaused: remove finish " + printActivity(activity))
                }
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG, "onActivityStopped: " + printActivity(activity))
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i(TAG, "onActivitySaveInstanceState: " + printActivity(activity))
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG, "onActivityDestroyed: " + printActivity(activity))
    }

    fun getTopCompatActivity(): AppCompatActivity? {
        //注意这里尽量在主线程获取
        if (lifeActivityList.isEmpty()) {//&& Looper.getMainLooper().thread == Thread.currentThread()){
            return null;
        }
        return lifeActivityList[lifeActivityList.size] as? AppCompatActivity;
    }

    fun getTopActivity(): Activity? {
        //注意这里尽量在主线程获取
        if (lifeActivityList.isEmpty()) {//&& Looper.getMainLooper().thread == Thread.currentThread()){
            return null;
        }
        return lifeActivityList[lifeActivityList.size]
    }

    fun <A : Activity> findActivity(c: Class<A>): A? {
        if (lifeActivityList.isEmpty()) {
            return null
        }
        for (a in lifeActivityList) {
            if (c.name == a.javaClass.name) {
                return a as A
            }
        }
        return null
    }

    fun <A : Activity> findActivitys(c: Class<A>): List<A> {
        val aList = ArrayList<A>();
        if (lifeActivityList.isEmpty()) {
            return aList
        }
        for (a in lifeActivityList) {
            if (c.name == a.javaClass.name) {
                aList.add(a as A)
            }
        }
        return aList
    }

}