package com.musongzi.test.vm

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.vm.CoreViewModel
import com.musongzi.test.MszTestApi
import com.musongzi.test.business.SimpleDataBusiness
import java.util.concurrent.TimeUnit

//@RequiresApi(Build.VERSION_CODES.N)
/*** created by linhui * on 2022/7/25 */
class TestMainIndexBusiness : MainIndexBusiness() {


    override fun afterHandlerBusiness() {
//        mExecutor.n
    }
    var livedata:LiveData<String>? = null

    override fun buildFragments(): List<Fragment> {

        iAgent.holderApiInstance<MszTestApi>()?.getApi()?.getArrayEngine(0,20)?.sub {
//
            Log.i(TAG, "buildFragments: ${it.data[0].title} " + javaClass.name)
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