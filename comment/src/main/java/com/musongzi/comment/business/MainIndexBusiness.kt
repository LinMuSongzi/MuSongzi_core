package com.musongzi.comment.business

import android.util.Log
import androidx.fragment.app.Fragment
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.page.IDataEngine

/*** created by linhui * on 2022/7/20 */
open class MainIndexBusiness : MainBottomBusiness() {

    override fun buildFragments(): List<Fragment> {
        Log.i(TAG, "buildFragments: " + javaClass.name)
//        iAgent.holderApiInstance<Api>()

        return ArrayList()
    }


}