package com.musongzi.test.vm

import android.util.Log
import androidx.fragment.app.Fragment
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.vm.CoreViewModel
import com.musongzi.test.Api

/*** created by linhui * on 2022/7/25 */
class TestMainIndexBusiness : MainIndexBusiness() {


    override fun afterHandlerBusiness() {
        super.afterHandlerBusiness()

//        NormalFragmentActivity::class.java.startActivity()

//        Handler().post {
//            "haha".liveSaveStateObserver<Int>(iAgent){
//                Log.i(TAG, "buildFragments: test remote value = $it")
//            }
//        }


    }

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


        "haha".saveStateChange(iAgent,998777)


//        ArrayList<String>().toArray()

//        val clazz :KClass<ArrayEngine> = ArrayEngine::class
//        Log.i(TAG, "buildFragments: "+clazz.supertypes)
//        Log.i(TAG, "buildFragments: "+(clazz.annotations[0] as com.musongzi.core.annotation.CollecttionsEngine).emptyString)
//        for(vc in clazz.supertypes){
//
//            Log.i(TAG, "buildFragments: "+vc.arguments.let {
//                if(it.isNotEmpty()){
//                    (it[0].type?.classifier as KClass<*>)
//                }else{
//                    it
//                }
//            })
//
//        }


        return ArrayList()
    }

}