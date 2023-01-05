package com.mszsupport.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.mszsupport.comment.ActivityViewSupportImpl
import com.mszsupport.comment.ExtensionCoreMethod.getViewModelInstance
import com.mszsupport.comment.ExtensionCoreMethod.thisInstance
import com.mszsupport.itf.IActivityView
import com.mszsupport.itf.holder.IHolderActivityView
import com.mszsupport.itf.holder.getSafeAtivityView

class TestImplFragment : IHolderActivityView, Fragment() {

    private lateinit var activityView: IActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityView = ActivityViewSupportImpl(this, savedInstanceState)
        val vm = activityView.thisViewModelProvider()?.get(MyViewModel::class.java)


        activityView.getViewModelInstance<MyViewModel>()?.getHolderBusiness()//.getThisLifecycle()

        vm?.let {
            it.getHolderSavedStateHandle().getLiveData<String>("").observe(this){
                Log.i("123", "onCreate: ")
            }
        }

    }

    override fun getHolderActivityView(): IActivityView? = getSafeAtivityView()

}