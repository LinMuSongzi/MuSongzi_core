package com.musongzi.comment.bean

import android.net.Uri
import android.view.View
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/20  */
class SimpleCardInfo (
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