package com.musongzi.test.bean

import com.musongzi.core.base.bean.ICovertInfo

/*** created by linhui * on 2022/8/3 */
class AlbumInfo : SongArrayInfo(), ICovertInfo {

    var cover: String? = null

    var likeCount = 0
    override fun getCovert(): String? {
        return cover
    }

}