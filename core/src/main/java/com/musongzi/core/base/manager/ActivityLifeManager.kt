package com.musongzi.core.base.manager

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IEventManager
import com.musongzi.core.itf.ISingleManager
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.util.ActivityThreadHelp
import java.lang.reflect.Proxy

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

        fun getEventManager(): IEventManager {
            return MANAGER.managers[EVENT_MANGER] as IEventManager
        }

        const val EVENT_MANGER = "com.musongzi.core.base.manager.EventManager"


        fun <T> registerEvent(e: Class<T>, h: () -> T) {
            if (!e.isInterface) {
                Log.i("registerEvent", ": 1 ")
                return
            }
            getEventManager().put(e, h)
        }

        fun <T> IHolderLifecycle.registerEvent(e: Class<T>, h: () -> T) {
            if (!e.isInterface) {
                Log.i("registerEvent", ": 1 ")
                return
            }
            Log.i("registerEvent", ": 2 ")
            getThisLifecycle()?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    Log.i("registerEvent", ": 3 ")
                    getEventManager().put(e, h)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    Log.i("registerEvent", ": 4 ")
                    getEventManager().remove(e, h.invoke())
                }

            })
        }

        fun <T> Class<T>.event(): T? {
            return getEventManager().asInterface(this)
        }

    }

    private lateinit var managerStr: List<String>
    private val managers = HashMap<String, ISingleManager>()

    init {
        ActivityThreadHelp.getCurrentApplication().let {
            it.registerComponentCallbacks(this)
            (it as Application).registerActivityLifecycleCallbacks(this)
        }
        initManager();
    }


    private fun initManager() {
        managerStr = ArrayList<String>().let {
            it.add(EVENT_MANGER)
            it
        }

        for (v in managerStr) {
            val m = javaClass.classLoader!!.loadClass(v).newInstance() as ISingleManager
            m.onReady()
            managers[v] = m
        }
    }

    private val lifeActivityList = ArrayList<Activity>()

    fun getLifeActivityList():List<Activity>{
        return lifeActivityList.toList()
    }

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
        lifeActivityList.remove(activity)
        Log.i(TAG, "onActivityDestroyed: " + printActivity(activity))

    }

    fun getTopCompatActivity(): AppCompatActivity? {
        //注意这里尽量在主线程获取
        if (lifeActivityList.isEmpty()) {//&& Looper.getMainLooper().thread == Thread.currentThread()){
            return null;
        }
        return lifeActivityList[lifeActivityList.size - 1] as? AppCompatActivity;
    }

    fun getTopActivity(): Activity? {
        //注意这里尽量在主线程获取
        if (lifeActivityList.isEmpty()) {//&& Looper.getMainLooper().thread == Thread.currentThread()){
            return null;
        }
        return lifeActivityList[lifeActivityList.size - 1]
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