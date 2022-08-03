package com.musongzi.music.impl

import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.*

/*** created by linhui * on 2022/7/28 */
internal interface ISmartPlayQueueManager:IPlayQueueManager,IPlayObsavable {
    fun playMusic(info:IMediaPlayInfo,musicArray: IMusicArray)
    fun playMusic(stringUrl: String, musicArray: IMusicArray)
    fun pauseMusic(musicArray: IMusicArray)
    fun stopMusic(musicArray: IMusicArray)
    fun joinHistory(playInfoNow: IMediaPlayInfo?)

}