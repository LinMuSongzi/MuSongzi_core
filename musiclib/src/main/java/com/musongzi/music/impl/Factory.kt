package com.musongzi.music.impl

import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.itf.IAttribute
import com.musongzi.music.itf.*

/*** created by linhui * on 2022/7/28  */
object Factory {


    internal fun <A:IAttribute> createPlayMusicController(any: IMusicArray<A>): IPlayController {
        return ProxyPlayController(PlayQueueManagerImpl.getInstance(), any)
    }


    fun createNativePlayer(): IPlayerManager {
        return ShamPlayManager()
    }

    //
//    fun normalPlayQueueValues(): Set<String>? {
//        TODO("Not yet implemented")
//    }
//
    fun getMusicClassLoader(): ClassLoader {
        return Factory::class.java.classLoader
    }


    fun buildInstanceManagerHelp(readyNow: () -> IMusicInit): ManagerInstanceHelp {

        return object : ManagerInstanceHelp {
            override fun instance(): InstanceManager {
                return PlayQueueManagerImpl()
            }

            override fun readyNow(my: InstanceManager): Any {
                return readyNow.invoke()
            }

        }


    }

}