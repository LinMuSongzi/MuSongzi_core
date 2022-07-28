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
    }
    var name: String? = null
    var mediaId: String = ""

    constructor(mediaId: String) : this() {
        this.mediaId = mediaId
    }


    override fun getAttributeId(): String {
        return if (mediaId.isEmpty()) {
            ("" + hashCode());
        } else {
            mediaId
        }
    }


}