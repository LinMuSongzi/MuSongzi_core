package com.musongzi.music.impl

import android.database.Observable
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.itf.IAttribute
import com.musongzi.music.itf.*

/*** created by linhui * on 2022/7/28  */
object Factory {


    internal fun createPlayMusicController(any: IMusicArray): IPlayController {
        return ProxyPlayController(PlayQueueManagerImpl.getInstance(), any)
    }


    fun createNativePlayer(): IPlayerManager {
        return FakerPlayManager()
    }

    //
//    fun normalPlayQueueValues(): Set<String>? {
//        TODO("Not yet implemented")
//    }
//
    fun getMusicClassLoader(): ClassLoader {
        return Factory::class.java.classLoader
    }


    fun buildInstanceManagerHelp(readyNow: () -> IMusicInit): ManagerUtil.ManagerInstanceHelp {

        return object : ManagerUtil.ManagerInstanceHelp {
            override fun instance(): InstanceManager {
                return PlayQueueManagerImpl()
            }

            override fun readyNow(my: InstanceManager): Any {
                return readyNow.invoke()
            }

        }


    }

}