package com.musongzi.comment.bean

import android.net.Uri
import com.musongzi.comment.ExtensionMethod.getResUri
import com.musongzi.core.base.bean.BaseChooseBean

/*** created by linhui * on 2022/7/20 */
class ImageLoadBean() : BaseChooseBean() {

     lateinit var uri: Uri

    private fun convert(res:Int){
        uri = res.getResUri()
    }

    private fun convert(res:String){
        uri = Uri.parse(res)
    }

    private fun convert(res:Uri){
        uri = res
    }

    constructor(res:Any):this(){
        when (res) {
            is Int -> {
                convert(res)
            }
            is Uri -> {
                convert(res)
            }
            is String -> {
                convert(res)
            }
        }
    }


}