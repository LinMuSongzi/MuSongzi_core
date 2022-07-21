package com.musongzi.comment.bean.response

import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/21 */
interface IItemBean {


    fun getItemCover():String?

    fun getItemTitle():String

    fun getItemTitleDescribe():String?

    fun getItemContent():String?

    fun getItemLabels():List<StringChooseBean>?


}