package com.musongzi.music.itf

import android.net.Uri
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IHolderRes

/*** created by linhui * on 2022/8/1 */
interface IMediaPlayInfo : IAttribute, IHolderRes {

    fun setUri(uri: Uri)

    fun setRes(res:Int)

    fun setDataString(dataString: String)

}