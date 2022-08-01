package com.musongzi.music.itf

import com.musongzi.music.bean.MusicPlayInfo

/*** created by linhui * on 2022/7/28 */
interface PlayMusicObervser {

    fun onStateChange(state:String,info: MusicPlayInfo)

}