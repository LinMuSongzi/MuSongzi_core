package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute

/*** created by linhui * on 2022/7/28 */
internal interface ISmartPlayQueueManager : IPlayQueueManager, IPlayObsavable {
    fun playMusic(info: IMediaPlayInfo, musicArray: IMusicArray<IMediaPlayInfo>)
    fun playMusic(stringUrl: String, musicArray: IMusicArray<IMediaPlayInfo>)
    fun pauseMusic(musicArray: IMusicArray<IMediaPlayInfo>)
    fun stopMusic(musicArray: IMusicArray<IMediaPlayInfo>)
    fun joinHistory(playInfoNow: IMediaPlayInfo?)

}