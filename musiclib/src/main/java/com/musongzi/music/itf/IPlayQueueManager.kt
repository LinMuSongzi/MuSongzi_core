package com.musongzi.music.itf

import com.musongzi.music.bean.MusicPlayInfo
import com.musongzi.music.impl.MusicArrayImpl

/*** created by linhui * on 2022/7/28  */
interface IPlayQueueManager : IHolderPlayController {

    fun generatedConfigPlayQueue(config: Array<String>? = null)

    fun getPlayingArray(): MusicArrayImpl

    fun getMusicArrayByMusicItem(musicPlayInfo: MusicPlayInfo)

    fun getPlayingArrayId(): String


}