package com.musongzi.test.business

import android.database.Observable
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IAttribute
import com.musongzi.music.impl.IMusicInit
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.IPlayerManager

/*** created by linhui * on 2022/8/1 */
class MusicConfigHelpBusines: BaseLifeBusiness<IAgent>(),IMusicInit {
    override fun createPlayerManager(type: Int): IPlayerManager {
        TODO("Not yet implemented")
    }

    override fun buildPlayerManagerType(): Int {
        TODO("Not yet implemented")
    }

    override fun onInit(manager: IPlayQueueManager): Array<String>? {
        TODO("Not yet implemented")
    }

    override fun onGeneratedConfigPlayQueues(): Observable<Set<String>>? {
        TODO("Not yet implemented")
    }

    override fun createArrayParent(info: Any): IAttribute {
        TODO("Not yet implemented")
    }

    override fun createMusicArray(name: Any): IMusicArray {
        TODO("Not yet implemented")
    }

    override fun createMusicInfo(url: String): IMediaPlayInfo {
        TODO("Not yet implemented")
    }
}