package com.musongzi.music.itf.small

import com.musongzi.music.bean.MusicPlayInfo

/*** created by linhui * on 2022/7/28 */
interface OnPlayCountListener {
    fun onKeepPlay(pre: MusicPlayInfo, now: MusicPlayInfo)

    fun repeat(musicPlayInfo: MusicPlayInfo);
}