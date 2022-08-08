package com.musongzi.test.business

import android.util.Log
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IViewInstance
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.music.bean.Container
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

    companion object {
        const val SIMPLE_ARRAY = "simple_music_array"
    }

    private val observableArray = Observable.create<Set<String>> {
        it.onNext(HashSet<String>().apply {
            SIMPLE_ARRAY
        })
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

    override fun createMusicArray(name: Any): IMusicArray<IAttribute> {
        val n = name as String
        return MusicArrayBusiness(createDataProxy(n), n).let {
            it.afterHandlerBusiness()
            it
        } as IMusicArray<IAttribute>
    }

    private fun createDataProxy(n: String): MusicDataProxy<IMediaPlayInfo, AlbumMusicsRemoteBean> {
        when (n) {
            SIMPLE_ARRAY -> {
                return object : MusicDataProxy<IMediaPlayInfo, AlbumMusicsRemoteBean> {
                    override fun getRemoteData(page: Int): Observable<AlbumMusicsRemoteBean>? {
//                        getHolderSavedStateHandle()
                        return Observable.create<AlbumMusicsRemoteBean> {
                            val bean = AlbumMusicsRemoteBean();
                            bean.dataBean.list.add(MusicDetailRemoteBean().apply {
                                songName = "闪歌之歌"
                                url =
                                    "https://flashfiles.oss-cn-shenzhen.aliyuncs.com/d795e90451627c0ed909c285d79f9fe2.mp3"
                            })
                            bean.dataBean.list.add(MusicDetailRemoteBean().apply {
                                songName = "爱你一万年"
                                url =
                                    "https://flashfiles.oss-cn-shenzhen.aliyuncs.com/dnsRXVtSobKAPSyXAAkA5_THW5s594.mp3"
                            })
                            bean.dataBean.list.add(MusicDetailRemoteBean().apply {
                                songName = "年少有为"
                                url =
                                    "https://flashfiles.oss-cn-shenzhen.aliyuncs.com/53f5df9060d63117d05ba405aa3d343a.mp3"

                            })
                            it.onNext(bean)
                        }
                    }

                    override fun handlerData(t: MutableList<IMediaPlayInfo>, action: Int) {
                        val o = Observable.create<ObservableMusicArrayEntity> {
                            it.onNext(ObservableMusicArrayEntity(n,t,action))
                        }
                        getHolderSavedStateHandle().getLiveData<Observable<ObservableMusicArrayEntity>>(n).value = o
                    }

                    override fun transformDataToList(entity: AlbumMusicsRemoteBean): List<IMediaPlayInfo> {
                        val midData = ArrayList<IMediaPlayInfo>();
                        for (v in entity.dataBean.list) {
                            val sInfo = SongInfo();
                            sInfo.musicName = v.songName
                            sInfo.setUriString(v.url)
                            midData.add(sInfo)
                        }
                        return midData;
                    }

                }
            }
            else -> {
                TODO()
            }
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


    internal class MusicArrayBusiness(
        proxy: MusicDataProxy<IMediaPlayInfo, AlbumMusicsRemoteBean>,
        name: String
    ) : BaseMapBusiness<IViewInstance>(), IMusicArray<IMediaPlayInfo> {

        private var impl: IMusicArray<IMediaPlayInfo> = MusicArrayImpl(name,proxy)

        private lateinit var pageEngine: IPageEngine<IMediaPlayInfo>

        @Deprecated("过期")
        fun setId(name: String) {
            (impl as? Container<*>)?.apply {
                attributeId = name
            }
        }

        override fun thisPlayIndex(): Int = impl.getHolderPageEngine().thisStartPage()

        override fun changeThisPlayIndex(index: Int) {
            impl.changeThisPlayIndex(index)
        }

        override fun changeThisPlayIndexAndAdd(stringUrl: String) {
            impl.changeThisPlayIndexAndAdd(stringUrl)
        }

        override fun contains(att: IAttribute, find: ((IAttribute) -> Boolean)?): Boolean {
            return impl.contains(att, find)
        }

        override fun getAttributeId(): String {
            return impl.attributeId
        }

        override fun realData(): List<IMediaPlayInfo> {
            return impl.realData()
        }

        override fun getPlayController(): IPlayController? {
            return impl.getPlayController()
        }

        override fun getHolderPageEngine(): IPageEngine<IMediaPlayInfo> {
            return pageEngine;
        }


    }

    data class ObservableMusicArrayEntity(var name: String, val array: List<IMediaPlayInfo>, val action: Int)


}