package com.mszsupport.comment

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.mszsupport.itf.IViewInstance

class ViewInstanceImpl(var lifecycleOwner: LifecycleOwner? = null) :IViewInstance {


    override fun runOnUiThread(runnable: Runnable) {
        if (lifecycleOwner != null) {
            val activity = (lifecycleOwner as? Activity) ?: (lifecycleOwner as? Fragment)?.requireActivity()
            activity?.runOnUiThread(runnable)
        }else{
            Handler(Looper.getMainLooper()).post(runnable)
        }
    }
}