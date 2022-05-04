package com.musongzi.core.base

import android.app.AppOpsManager
import android.content.Context
import androidx.startup.Initializer
import com.musongzi.core.base.manager.ActivityLifeManager

class HolderInit :Initializer<ActivityLifeManager>{

    override fun create(context: Context): ActivityLifeManager {
       return ActivityLifeManager.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}