package com.musongzi.music.itf

import android.net.Uri
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IHolderRes

/*** created by linhui * on 2022/8/1
 * 
 * 当前媒体信息的抽象接口
 * 描述了媒体信息的最基本信息
 * [IAttribute.getAttributeId] 媒体最重要的唯一id，默认为 id 为 uri 地址
 * */
interface IMediaPlayInfo : IAttribute, IHolderRes {

    fun setUri(uri: Uri)

    fun setRes(res:Int)

    fun setUriString(dataString: String)

}