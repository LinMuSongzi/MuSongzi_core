package com.musongzi.comment.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.comment.bean.ImageLoadBean
import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.itf.IAgent
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.view.IMainBottomView
import com.musongzi.comment.business.MainBottomBusiness

/*** created by linhui * on 2022/7/20 */
class MainBottomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), IMainBottomView {

    var defualtIndex = 0;

    override fun getRecycleView(): RecyclerView {
        return this
    }

    private var iAgent: IAgent? = null
    var business: MainBottomBusiness? = null

    fun initMainBottomView(iAgent: IAgent,array: Array<MainBottomView.Info>) {
        if (business == null) {
            this.iAgent = iAgent
            this.business = InjectionHelp.injectBusiness(MainBottomBusiness::class.java, this)
        }
        business?.buildDataBySize(array = array)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindow: 123123123213")
    }

    override fun getHolderClient() = iAgent?.getHolderClient()

    override fun getMainLifecycle() = iAgent?.getMainLifecycle()

    override fun getThisLifecycle() = iAgent?.getThisLifecycle()

    override fun showDialog(msg: String?) {
        iAgent?.showDialog(msg)
    }

    override fun disimissDialog() {
        iAgent?.disimissDialog()
    }

    override fun disconnect() {
        iAgent?.disconnect()
    }


    data class Info(
        var title:String,
        var titleColor: Pair<Int, Int>,
        val imageBeans: Pair<ImageLoadBean, ImageLoadBean>,
        val onClick: (View) -> Unit,
    ) : BaseChooseBean() {

        fun getImage(): Uri {
            return if (isChoose()) {
                imageBeans.second.uri
            } else {
                imageBeans.first.uri
            }
        }

        fun getTitleColor():Int{
            return if(isChoose()){
                titleColor.second
            }else{
                titleColor.first
            }
        }

    }

    companion object {
        const val TAG  ="MainBottomView"
    }

}