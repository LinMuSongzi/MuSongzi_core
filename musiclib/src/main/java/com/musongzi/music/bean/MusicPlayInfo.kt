package com.musongzi.music.bean

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28 */
class MusicPlayInfo(url: String, val mediaConfig: MediaConfig? = null) : MediaPlayInfo(url) {


    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is MusicPlayInfo && attributeId == other.attributeId) {
                return true
            } else {
                super.equals(other)
            }
        } ?: false
    }


}