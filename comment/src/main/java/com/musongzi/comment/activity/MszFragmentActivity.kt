package com.musongzi.comment.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.musongzi.comment.business.SupproActivityBusiness
import com.musongzi.core.ExtensionCoreMethod.layoutInflater
import com.musongzi.core.base.business.itf.IHolderSupportActivityBusiness
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness
import com.musongzi.core.itf.IViewInstance
import com.musongzi.core.itf.holder.IHolderArguments
import com.musongzi.core.itf.holder.IHolderContext

/*** created by linhui * on 2022/7/6 */
open class MszFragmentActivity : AppCompatActivity(), IHolderContext,
    IHolderSupportActivityBusiness,IViewInstance {

    lateinit var business: ISupprotActivityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBusiness(savedInstanceState)
    }

    @Deprecated("请使用 ",replaceWith = ReplaceWith("setChildMainView"))
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    @Deprecated("请使用 ",replaceWith = ReplaceWith("setChildMainView"))
    override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    @Deprecated("请使用 ",replaceWith = ReplaceWith("setChildMainView"))
    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
    }

    protected fun setChildMainView(view: View?) {
//        super.setContentView(view)
        view?.apply {
            (business.getHolderDataBinding().root as ViewGroup).addView(this)
        }

    }

    protected fun setChildMainView(layoutResID: Int) {
        (business.getHolderDataBinding().root as ViewGroup).apply {
            addView(layoutResID.layoutInflater(this))
        }
    }

    protected fun setChildMainView(view: View?, params: ViewGroup.LayoutParams?) {
        view?.apply {
            (business.getHolderDataBinding().root as ViewGroup).addView(this,params)
        }

    }

    private fun checkBusiness(savedInstanceState:Bundle?) {
        business = SupproActivityBusiness.create(savedInstanceState, this) ?: SupproActivityBusiness.create2(savedInstanceState,this)
        business.checkEvent()
    }


    override fun getHolderContext() = this


    fun goBack(v: View) {
        finish()
    }

    override fun getHolderSupprotActivityBusiness() = business

    override fun topViewModelProvider(): ViewModelProvider? {
        return business.topViewModelProvider()
    }

    override fun thisViewModelProvider(): ViewModelProvider? {
        return business.thisViewModelProvider()
    }

}