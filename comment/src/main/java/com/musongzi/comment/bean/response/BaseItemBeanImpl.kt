package com.musongzi.comment.bean.response

import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/21 */
open class BaseItemBeanImpl : BaseChooseBean(), IItemBean,IFlagBean {

    private var cover: String? = null
    private var title = ""
    private var titleDescribe: String? = null
    private var content: String? = null
    private var labels: List<StringChooseBean>? = null
    private var flag = 0

    override fun getItemCover(): String? = cover

    override fun getItemTitle(): String = title

    override fun getItemTitleDescribe(): String? = titleDescribe

    override fun getItemContent(): String? = content

    override fun getItemLabels(): List<StringChooseBean>? = labels
    override fun getItemFlag() = flag


}