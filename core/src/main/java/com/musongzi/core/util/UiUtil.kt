package com.musongzi.core.util

import android.os.Handler
import android.os.Looper

/*** created by linhui * on 2022/9/20 */

val HANDLER_UI = Handler(Looper.getMainLooper())

object UiUtil {

    fun post(runnable: Runnable) {
        HANDLER_UI.post(runnable)
    }

    fun post(time: Long, runnable: Runnable) {
        HANDLER_UI.postDelayed(runnable, time)
    }

}