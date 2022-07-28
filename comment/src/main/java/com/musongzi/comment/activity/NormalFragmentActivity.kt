package com.musongzi.comment.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.musongzi.comment.business.SupproActivityBusiness
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness
import com.musongzi.core.itf.holder.IHolderArguments
import com.musongzi.core.itf.holder.IHolderContext

/*** created by linhui * on 2022/7/6 */
open class NormalFragmentActivity : AppCompatActivity(), IHolderContext{

    lateinit var business: ISupprotActivityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBusiness(savedInstanceState)
    }

    private fun checkBusiness(savedInstanceState:Bundle?) {
        business = SupproActivityBusiness.create(savedInstanceState, this) ?: SupproActivityBusiness.create2(savedInstanceState,this)
        business.checkEvent()
    }


    override fun getHolderContext() = this


    fun goBack(v: View) {
        finish()
    }


}