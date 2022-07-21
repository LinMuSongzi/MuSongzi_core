package com.musongzi.comment.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.musongzi.comment.business.SupproActivityBusiness
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness
import com.musongzi.core.itf.holder.IHolderContext

/*** created by linhui * on 2022/7/6 */
open class NormalFragmentActivity : AppCompatActivity(), IHolderContext {

    lateinit var business: ISupprotActivityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        business = SupproActivityBusiness.create(savedInstanceState, this)
        checkBusiness()
    }

    private fun checkBusiness() {
        business.checkEvent()
    }


    override fun getHolderContext() = this


    fun goBack(v: View) {
        finish()
    }


}