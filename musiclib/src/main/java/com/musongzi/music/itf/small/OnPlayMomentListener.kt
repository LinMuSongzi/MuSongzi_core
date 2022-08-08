package com.musongzi.music.itf.small

import com.musongzi.music.bean.MediaPlayInfo
import com.musongzi.music.itf.IMediaPlayInfo

/*** created by linhui * on 2022/7/28 */
interface OnPlayMomentListener {


    fun onBuffer(info: IMediaPlayInfo,byte: Byte,any: Any?)

    fun onPlaying(info: IMediaPlayInfo)


}