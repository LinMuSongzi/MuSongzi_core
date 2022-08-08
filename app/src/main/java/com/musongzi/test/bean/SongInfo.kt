package com.musongzi.test.bean

import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.bean.ICovertInfo
import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.itf.IMediaPlayInfo

/*** created by linhui * on 2022/8/3 */
class SongInfo : MusicPlayInfoImpl(),ICovertInfo {

    var artist :String? = ""

    private var mCover:String? = null

    var createTime = System.currentTimeMillis()

    var album : AlbumInfo? = null

    var styles:List<StringChooseBean> = ArrayList()


    override fun getCover(): String? {
        return mCover
    }


}