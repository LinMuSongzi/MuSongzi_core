package com.musongzi.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

//class ActivityThreadHelp {


@SuppressLint("PrivateApi", "DiscouragedPrivateApi")
fun getCurrentApplication(): Context {
    val classLoader = Context::class.java.classLoader
    val tClass = classLoader!!.loadClass("android.app.ActivityThread")
    return tClass.getDeclaredMethod("currentApplication").invoke(null) as Context
}