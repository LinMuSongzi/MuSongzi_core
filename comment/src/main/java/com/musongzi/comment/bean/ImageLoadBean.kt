package com.musongzi.comment.bean

import android.net.Uri
import android.util.Log
import com.musongzi.comment.ExtensionMethod.getResUri
import com.musongzi.comment.ExtensionMethod.getResUriString
import com.musongzi.comment.util.ApkUtil.TAG
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/20 */
class ImageLoadBean() : BaseChooseBean() {


    var uriStr : String? = null
    var res:Int = 0

    private fun convert(res: Int) {
        this.res = res
//        uriStr = res.getResUriString()
    }

    private fun convert(uriStr: String) {
        this.uriStr = uriStr
    }

    constructor(res: Any) : this() {

        when (res) {
            is Int -> {
                convert(res)
            }
            is Uri -> {
                convert(res.toString())
            }
            is String -> {
                convert(res)
            }
        }
    }


}