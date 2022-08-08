package com.musongzi.test.business

import android.util.Log
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IViewInstance
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.impl.IMusicInit
import com.musongzi.music.impl.MusicArrayImpl
import com.musongzi.music.impl.MusicDataProxy
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IMusicArray
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.test.bean.SongArrayInfo
import com.musongzi.test.bean.SongInfo
import com.musongzi.test.bean.respone.AlbumMusicsRemoteBean
import com.musongzi.test.bean.respone.MusicDetailRemoteBean
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/8/1 */
class MusicConfigHelpBusines : BaseMapBusiness<IViewInstance>(), IMusicInit {

    private val observableArray = Observable.create<Set<String>> {

    }


    override fun onInit(manager: IPlayQueueManager): Array<String>? {
        Log.i(TAG, "onInit: 初始化成功")
        return null
    }

    override fun onGeneratedConfigPlayQueues(page: Int): Observable<Set<String>>? {
        return observableArray
    }

    override fun createArrayParent(info: Any): IAttribute {
        return SongArrayInfo().apply {
            name = info.toString()
        }
    }

    override fun createMusicArray(name: Any): IMusicArray<*,*> {
        return MusicArrayBusiness().let {
            it.setId(name)
            it.afterHandlerBusiness()
            it
        }
    }

    override fun createMusicInfo(url: String): IMediaPlayInfo {
        return SongInfo().apply {
            setUriString(url)
        }
    }

    override fun onPlayJoinHistory(playInfoNow: IMediaPlayInfo?) {
        TODO("Not yet implemented")
    }


    class MusicArrayBusiness: BaseMapBusiness<IViewInstance>() ,IMusicArray<SongInfo,AlbumMusicsRemoteBean>{


        private lateinit var name: String
        private lateinit var impl : MusicArrayImpl<SongInfo,AlbumMusicsRemoteBean>
        private lateinit var proxy:MusicDataProxy<SongInfo,AlbumMusicsRemoteBean>

        fun setId(name: Any) {
            this.name = name as String
        }

        override fun thisPlayIndex(): Int  = impl.thisStartPage()

        override fun changeThisPlayIndex(index: Int) {

        }

        override fun changeThisPlayIndexAndAdd(stringUrl: String) {

        }

        override fun contains(att: IAttribute, find: ((IAttribute) -> Boolean)?): Boolean {
            TODO("Not yet implemented")
        }

        override fun getPageEngine(): IPageEngine<SongInfo> {
            TODO("Not yet implemented")
        }

        override fun getAttributeId(): String {
            TODO("Not yet implemented")
        }

        override fun pageSize(): Int {
            TODO("Not yet implemented")
        }

        override fun thisStartPage(): Int {
            TODO("Not yet implemented")
        }

        override fun getRemoteData(page: Int): Observable<AlbumMusicsRemoteBean>? {
            return Observable.create<AlbumMusicsRemoteBean> {
                var bean = AlbumMusicsRemoteBean();
                bean.dataBean.list.add(MusicDetailRemoteBean())
                bean.dataBean.list.add(MusicDetailRemoteBean())
                bean.dataBean.list.add(MusicDetailRemoteBean())
                it.onNext(bean)
            }
        }

        override fun handlerData(t: MutableList<SongInfo>?, action: Int) {
            Log.i(TAG, "handlerData: $action")
        }

        override fun transformDataToList(entity: AlbumMusicsRemoteBean): List<SongInfo> {
           val list = ArrayList<SongInfo>()
            for(v in entity.dataBean.list){
                list.add(SongInfo().apply {
                   this.setUriString(v.songUrl)
                    this.musicName = v.songName
                })
            }
            return list
        }

        override fun realData(): List<SongInfo> {
           return impl.realData()
        }

        override fun getPlayController(): IPlayController? {
           return impl.getPlayController()
        }


    }

}