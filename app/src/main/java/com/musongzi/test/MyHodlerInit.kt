package com.musongzi.test

import android.content.Context
import androidx.startup.Initializer
import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.HodlerInit
import com.musongzi.core.base.manager.ActivityLifeManager

class MyHodlerInit : Initializer<StringChooseBean> {
    override fun create(context: Context): StringChooseBean {
      return  StringChooseBean()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
       return ArrayList<Class<out Initializer<*>>>().let {
           it.add(HodlerInit::class.java)
           it
       }
    }
}