package com.musongzi.test.business

import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IViewInstance
import com.musongzi.music.impl.IMusicInit
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.IPlayerManager
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/8/1 */
class MusicConfigHelpBusines: BaseMapBusiness<IViewInstance>(),IMusicInit {

    override fun onInit(manager: IPlayQueueManager): Array<String>? {
        TODO("Not yet implemented")
    }

    override fun onGeneratedConfigPlayQueues(page: Int): Observable<Set<String>>? {
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

    override fun observerOnPlayJoinHistory(playInfoNow: IMediaPlayInfo?) {
        TODO("Not yet implemented")
    }
}