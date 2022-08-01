package com.musongzi.music.bean

import android.net.Uri
import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28 */
open class MediaPlayInfo() : IAttribute {

    var stringUrl: String? = ""
    set(value) {
        if(mediaId.isEmpty() && value!=null){
            mediaId = value
        }
        field = value
    }
    var name: String? = null
    var mediaId: String = ""

    constructor(uri: String) : this() {
        this.stringUrl = uri
    }


    override fun getAttributeId(): String {
        return mediaId
    }


}