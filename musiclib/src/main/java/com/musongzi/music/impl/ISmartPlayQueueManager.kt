package com.musongzi.music.impl

import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayObsavable
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.PlayMusicObervser

/*** created by linhui * on 2022/7/28 */
internal interface ISmartPlayQueueManager:IPlayQueueManager,IPlayObsavable {

    fun playMusic(stringUrl: String, musicArray: IMusicArray)
    fun pauseMusic(musicArray: IMusicArray)
    fun stopMusic(musicArray: IMusicArray)

}