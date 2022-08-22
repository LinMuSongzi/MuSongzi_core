package com.musongzi.comment.business

import android.util.Log
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.holder.IHolderLifecycle
import kotlin.math.abs

/**
 * 防止重复点击
 */
class DoubleLimiteBusiness : BaseMapBusiness<IHolderLifecycle>() {

    var map = HashMap<String, Long>()

    var limiter = LIMITER

    companion object {
        const val LIMITER = 1200
    }

    fun check(key: String, runnable: Runnable) {
        val v = map[key]
        if (v == null || abs(v - System.currentTimeMillis()) > limiter) {
            map[key] = System.currentTimeMillis()
            runnable.run()
            Log.i(TAG, "正在触发 check: 1 " + hashCode())
        } else {
            Log.i(TAG, "时间还没到无法点击 check: 2 " + hashCode())
        }
    }

}