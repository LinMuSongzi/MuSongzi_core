package com.musongzi.music.itf

import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.bean.MediaPlayInfo
import com.musongzi.music.bean.MusicPlayInfo
import com.musongzi.music.itf.small.*

/*** created by linhui * on 2022/7/28  */
interface IPlayQueueManager : IHolderPlayController, InstanceManager {


    override fun getPlayController(): IPlayController

    fun generatedConfigPlayQueues(config: Set<String>? = null)

    /**
     * 获取当前播放的音乐队列列表
     */
    fun getPlayingQueue(): IAttributeArray<MusicPlayInfo, Any>?

    /**
     * 根据当前的音乐信息获取所属队列
     */
    @Deprecated("暂时先标记过时~")
    fun getMusicQueueByMusicItem(info: MusicPlayInfo): Set<IMusicArray>?

    /**
     * 获取当前播放队列信息
     */
    fun getPlayingQueueName(): String?

    fun getPlayingInfo(): MediaPlayInfo?

    fun getListenerManager(onComplete: String):ListenerManager

    companion object {
        const val PLAYER_NATIVE = 1
        const val MANAGER_ID = 100

    }


    interface ListenerManager{
        fun addOnPlayCompleteListener(life:ILifeObject?,onPlayCompleteListener: OnPlayCompleteListener)
        fun addOnPlayCountListener(life:ILifeObject?,onPlayCountListener: OnPlayCountListener)
        fun addOnPlayLifeListener(life:ILifeObject?,onPlayLifeListener: OnPlayLifeListener)
        fun addOnReadyListener(life:ILifeObject?,onReadyListener: OnReadyListener)
        fun addOnPlayChangeListener(life:ILifeObject?,onPlayChangeListener: OnPlayChangeListener)
    }



}