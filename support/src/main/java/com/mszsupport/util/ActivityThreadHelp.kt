package com.mszsupport.util

import android.annotation.SuppressLint
import android.content.Context
import java.lang.reflect.Method

//class ActivityThreadHelp {

object ActivityThreadHelp {


    private val mActivityThreadGetMethod: Method by lazy {
        val classLoader = Context::class.java.classLoader
        val method = classLoader!!.loadClass("android.app.ActivityThread").getDeclaredMethod("currentApplication")
        method
    }

    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    @JvmStatic
    fun getCurrentApplication(): Context {
        return mActivityThreadGetMethod.invoke(null) as Context
    }
}
