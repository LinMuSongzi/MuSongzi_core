package com.musongzi.test.business

import android.util.Log
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.IViewInstance
import com.musongzi.music.itf.IMusicInit
import com.musongzi.music.itf.RemoteDataPacket
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IMusicArray
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

    override fun <I : IMediaPlayInfo, D> createMusicDataProxy(name: String): RemoteDataPacket<I, D> {
        return createDataProxy(name) as RemoteDataPacket<I, D>
    }

    override fun <I : IMediaPlayInfo> createTrackImpl(name: String): IMusicArray<I>? {
        return null
    }

//    override fun createMusicArray(name: Any): IMusicArray<IAttribute> {
//        val n = name as String
//        return MusicArrayBusiness(createDataProxy(n), n).let {
//            it.afterHandlerBusiness()
//            it
//        } as IMusicArray<IAttribute>
//    }

    private fun createDataProxy(n: String): RemoteDataPacket<IMediaPlayInfo, AlbumMusicsRemoteBean> {
        when (n) {
            SIMPLE_ARRAY -> {
                return object : RemoteDataPacket<IMediaPlayInfo, AlbumMusicsRemoteBean> {
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
//        TODO("Not yet implemented")
        Log.i(TAG, "onPlayJoinHistory: ")
    }


    data class ObservableMusicArrayEntity(var name: String, val array: List<IMediaPlayInfo>, val action: Int)


}