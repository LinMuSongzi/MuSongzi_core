package com.musongzi.music.impl

import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.itf.ILifeObject
import com.musongzi.core.itf.page.ISource
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.PlayMusicObervser

/*** created by linhui * on 2022/7/28 */
internal class ProxyPlayController(val manager:ISmartPlayQueueManager, private val musicArray: IMusicArray) :IPlayController {

    override fun playMusic(stringUrl: String) {
        manager.playMusic(stringUrl,musicArray)
    }

    override fun pauseMusic() {
        manager.pauseMusic(musicArray)
    }

    override fun stopMusic() {
        manager.stopMusic(musicArray)
    }

    override fun observerState(life:ILifeObject?,p: PlayMusicObervser) {
        manager.observerState(life,p)
    }


}