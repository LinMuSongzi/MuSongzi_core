package com.musongzi.music.itf

import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.holder.IHolderLifecycle

/*** created by linhui * on 2022/7/28 */
interface IPlayController : IPlayObsavable {


    fun playMusic(stringUrl: String)

    fun pauseMusic()

    fun stopMusic()

    companion object {

        const val ON_START = "ON_START"
        const val ON_COMPLETE = "ON_COMPLETE";
        const val ON_PAUSE = "ON_PAUSE"
        const val ON_STOP = "ON_STOP"
        const val ON_BUFFER = "ON_BUFFER"
        const val ON_PLAYING = "ON_PLAYING"

    }


}