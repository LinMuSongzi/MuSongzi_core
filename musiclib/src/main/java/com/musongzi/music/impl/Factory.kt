package com.musongzi.music.impl

import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.music.bean.InstanceBean
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.impl.MusicArrayImpl
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.IPlayerManager

/*** created by linhui * on 2022/7/28  */
object Factory{


    internal fun createPlayMusicController(any: IMusicArray):IPlayController {
        return ProxyPlayController(PlayQueueManagerImpl.getInstance(),any)
    }



    fun createNativePlayer(): IPlayerManager {
        TODO("Not yet implemented")
    }
//
//    fun normalPlayQueueValues(): Set<String>? {
//        TODO("Not yet implemented")
//    }
//
    fun getMusicClassLoader():ClassLoader{
        TODO("Not yet implemented")
    }


}