package com.musongzi.music.bean

/*** created by linhui * on 2022/7/28 */
class MusicPlayInfo(id: String, val mediaConfig: MediaConfig? = null) : MediaPlayInfo(id) {


    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is MusicPlayInfo && super.mediaId == other.mediaId) {
                return true
            } else {
                super.equals(other)
            }
        } ?: false
    }


}