package com.musongzi.music.itf.small

import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
interface OnPlayCountListener {
    fun onKeepPlay(pre: MusicPlayInfoImpl, now: MusicPlayInfoImpl)

    fun repeat(musicPlayInfo: MusicPlayInfoImpl);
}