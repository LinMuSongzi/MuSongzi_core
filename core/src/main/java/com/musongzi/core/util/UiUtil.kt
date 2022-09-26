package com.musongzi.core.util

import android.os.Handler
import android.os.Looper

/*** created by linhui * on 2022/9/20 */
object UiUtil {
    fun post(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }
}