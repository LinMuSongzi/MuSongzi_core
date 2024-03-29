package com.musongzi.music.itf

import com.musongzi.core.itf.IAttribute
import com.musongzi.music.impl.Factory

/*** created by linhui * on 2022/8/1 */
interface IMusicInit {

    /**
     * 创建底层播放器
     */
    fun createPlayerManager(type: Int): IPlayerManager {
        TODO("Not yet implemented")
    }

    /**
     * 创建播放器方式,根据业务情况 。会在方法 [createPlayerManager]中返回type
     */
    fun buildPlayerManagerType(): Int = 0

    /**
     * 当框架创建成功时
     */
    fun onInit(manager: IPlayQueueManager): Array<String>?

    /**
     * 构建一组音乐队列根据观察者
     */
    fun onGeneratedConfigPlayQueues(page: Int): io.reactivex.rxjava3.core.Observable<Set<String>>?

    /**
     * 创建顶层队列属性
     */
    fun createArrayParent(info: Any): IAttribute

    fun <I : IMediaPlayInfo, D> createMusicDataProxy(name: String): RemoteDataPacket<I, D>

    fun <I : IMediaPlayInfo> createTrackImpl(name: String): IMusicArray<I>?

    /**
     * 创建音乐队里管理对象
     */
    fun <I : IMediaPlayInfo, D> createMusicArray(
        name: String,
        dataPacket: RemoteDataPacket<I, D>,
        trackImpl: IMusicArray<I>? = null
    ) = Factory.createNativeMusicArray(name, dataPacket, trackImpl)

    /**
     * 创建音乐实体
     */
    fun createMusicInfo(url: String): IMediaPlayInfo

    fun onPlayJoinHistory(playInfoNow: IMediaPlayInfo?)


}