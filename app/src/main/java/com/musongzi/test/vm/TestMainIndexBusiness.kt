package com.musongzi.test.vm

import android.util.Log
import androidx.fragment.app.Fragment
import com.musongzi.comment.business.MainIndexBusiness
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.test.Api

/*** created by linhui * on 2022/7/25 */
class TestMainIndexBusiness : MainIndexBusiness() {


    override fun buildFragments(): List<Fragment> {

        iAgent.holderApiInstance<Api>()?.getApi()?.getArrayEngine(0)?.sub {

            Log.i(TAG, "buildFragments: ${it[0].title} " + javaClass.name)

        }

        return ArrayList()
    }

}