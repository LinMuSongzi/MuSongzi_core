package com.musongzi.music.impl

import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.*

/*** created by linhui * on 2022/7/28 */
internal interface ISmartPlayQueueManager<A:IAttribute>:IPlayQueueManager,IPlayObsavable {
    fun playMusic(info:IMediaPlayInfo,musicArray: IMusicArray<A>)
    fun playMusic(stringUrl: String, musicArray: IMusicArray<A>)
    fun pauseMusic(musicArray: IMusicArray<A>)
    fun stopMusic(musicArray: IMusicArray<A>)
    fun joinHistory(playInfoNow: IMediaPlayInfo?)

}