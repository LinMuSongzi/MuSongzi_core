package com.musongzi.music.itf

import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
interface PlayMusicObervser {

    fun onStateChange(state: String, info: IMediaPlayInfo?, player: Any? = null, other: Any? = null)

}