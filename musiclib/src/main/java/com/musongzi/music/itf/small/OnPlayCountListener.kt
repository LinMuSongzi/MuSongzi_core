package com.musongzi.music.itf.small

import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.itf.IMediaPlayInfo

/*** created by linhui * on 2022/7/28 */
interface OnPlayCountListener {
    fun onKeepPlay(pre: IMediaPlayInfo, now: IMediaPlayInfo)

    fun repeat(musicPlayInfo: IMediaPlayInfo);
}