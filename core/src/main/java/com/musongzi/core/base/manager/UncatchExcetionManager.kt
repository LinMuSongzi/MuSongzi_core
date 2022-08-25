package com.musongzi.core.base.manager

import android.util.Log
import com.musongzi.core.util.WriteTxt

/*** created by linhui * on 2022/8/25 */
class UncatchExcetionManager :InstanceManager, Thread.UncaughtExceptionHandler {

    companion object{
        private const val TAG = "UncatchExcetion"
    }

    override fun onReady(a: Any?) {
        super.onReady(a)
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.i(TAG, "message: t = ${e.message}")
        for (error in e.stackTrace) {
            Log.i(TAG, "error: $error")
        }
        WriteTxt.exception(e)
    }


}