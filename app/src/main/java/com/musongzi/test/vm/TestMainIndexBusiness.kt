package com.musongzi.test.vm

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.musongzi.FutureDemo
import com.musongzi.FutureDemo.acceptEither
import com.musongzi.comment.ExtensionMethod.getSaveStateLiveData
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.vm.CoreViewModel
import com.musongzi.mExecutor
import com.musongzi.test.Api
import com.musongzi.test.business.SimpleDataBusiness
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.N)
/*** created by linhui * on 2022/7/25 */
class TestMainIndexBusiness : MainIndexBusiness() {


    override fun afterHandlerBusiness() {
//        mExecutor.n
    }
    var livedata:LiveData<String>? = null

    override fun buildFragments(): List<Fragment> {

        iAgent.holderApiInstance<Api>()?.getApi()?.getArrayEngine(0)?.sub {
//
            Log.i(TAG, "buildFragments: ${it[0].title} " + javaClass.name)
//
        }


        val save = (iAgent as CoreViewModel<*>).localSavedStateHandle()
        save.getLiveData<String>("haha").observe(iAgent.getThisLifecycle()!!) {
            Log.i(TAG, "buildFragments: test local value = $it")
        }
        save["haha"] = "1"


        "haha".saveStateChange(iAgent, 998777)

        livedata = liveData{
            val data = loadData()
            emit(data)

        }


        iAgent.getThisLifecycle()?.let {
            if(business.getTabs() == null){
                Log.i(TAG, "buildFragments: 还没有初始化")
            }
            business.observer(it){
                Log.i(TAG, "buildFragments: 我绑定啦~~~~~~")
            }
        }



        return ArrayList()
    }

    suspend fun loadData():String{
        TimeUnit.SECONDS.sleep(3)
        return "你好"
    }


    var business = SimpleDataBusiness()

}