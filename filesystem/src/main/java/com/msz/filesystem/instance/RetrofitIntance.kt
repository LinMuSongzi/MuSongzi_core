package com.msz.filesystem.instance

import android.util.Log
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.RetrofitManager
import java.lang.reflect.Method
import java.util.concurrent.TimeUnit

class RetrofitIntance: InstanceManager {
    companion object{
        const val URL2 = "http://192.168.1.106:8080/"
    }
    override fun onReady(a: Any?) {
        RetrofitManager.getInstance().init { URL2 }
    }

}