package com.musongzi.comment.bean

import android.net.Uri
import android.view.View
import com.musongzi.core.ExtensionCoreMethod.androidColorGet
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/20  */
class SimpleCardInfo(
    var title: String,
    var titleColorPair: Pair<Int, Int>,
    private val imageBeans: Pair<ImageLoadBean, ImageLoadBean>,
    val onClick: (View) -> Unit,
) : BaseChooseBean() {

    fun getImage(): Any? {
        return if (isChoose()) {
            imageBeans.second.uriStr ?: imageBeans.second.res
        } else {
            imageBeans.first.uriStr ?: imageBeans.first.res
        }
    }

    fun getTitleColor(): Int {
        return if (isChoose()) {
            titleColorPair.second.androidColorGet()
        } else {
            titleColorPair.first.androidColorGet()
        }
    }

}