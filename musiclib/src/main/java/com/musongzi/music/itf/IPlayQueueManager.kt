package com.musongzi.music.itf

import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerUtil.manager
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.impl.ISmartPlayQueueManager
import com.musongzi.music.itf.small.*
import kotlin.reflect.KClass

/*** created by linhui * on 2022/7/28  */
interface IPlayQueueManager : IHolderPlayController, InstanceManager {

    /**
     * 获取播放器播放
     */
    override fun getPlayController(): IPlayController

    fun generatedConfigPlayQueues(config: Set<String>? = null)

    /**
     * 获取当前播放的音乐队列列表
     */
    fun getPlayingQueue(): IMusicArray<IAttribute>?

    /**
     * 根据当前的音乐信息获取所属队列
     */
    @Deprecated("暂时先标记过时~")
    fun getMusicQueueByMusicItem(info: IMediaPlayInfo): Set<IMusicArray<IAttribute>>?

    /**
     * 获取当前播放队列信息
     */
    fun getPlayingQueueName(): String?

    fun getPlayingInfo(): IAttribute?

    fun getListenerManager(): ListenerManager

    companion object {
        const val MANAGER_ID = 100

        fun KClass<IPlayQueueManager>.playMusic(info:IMediaPlayInfo){
            MANAGER_ID.manager<IPlayQueueManager>().getPlayController().playMusicByInfo(info)
        }

        fun KClass<IPlayQueueManager>.playMusic(url:String){
            MANAGER_ID.manager<IPlayQueueManager>().getPlayController().playMusic(url)
        }

        fun KClass<IPlayQueueManager>.instance():IPlayQueueManager{
            return MANAGER_ID.manager()
        }

        fun KClass<IPlayQueueManager>.playController():IPlayController{
            return MANAGER_ID.manager<IPlayQueueManager>().getPlayController()
        }

        fun KClass<IPlayQueueManager>.playObsavable():IPlayObsavable{
            return MANAGER_ID.manager<ISmartPlayQueueManager<MusicPlayInfoImpl>>()
        }

        fun KClass<IPlayQueueManager>.listenerManager():ListenerManager{
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager()
        }

        fun KClass<IPlayQueueManager>.addOnPlayCompleteListener(life: ILifeObject?, onPlayCompleteListener: OnPlayCompleteListener){
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager().addOnPlayCompleteListener(life,onPlayCompleteListener)
        }
        fun KClass<IPlayQueueManager>.addOnPlayCountListener(life: ILifeObject?, onPlayCountListener: OnPlayCountListener){
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager().addOnPlayCountListener(life,onPlayCountListener)
        }
        fun KClass<IPlayQueueManager>.addOnPlayLifeListener(life: ILifeObject?, onPlayLifeListener: OnPlayLifeListener){
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager().addOnPlayLifeListener(life,onPlayLifeListener)
        }
        fun KClass<IPlayQueueManager>.addOnReadyListener(life: ILifeObject?, onReadyListener: OnReadyListener){
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager().addOnReadyListener(life,onReadyListener)
        }
        fun KClass<IPlayQueueManager>.addOnPlayMomentListener(life: ILifeObject?, onPlayMomentListener: OnPlayMomentListener){
            return MANAGER_ID.manager<IPlayQueueManager>().getListenerManager().addOnPlayMomentListener(life,onPlayMomentListener)
        }

    }


    interface ListenerManager {
        fun addOnPlayCompleteListener(life: ILifeObject?, onPlayCompleteListener: OnPlayCompleteListener)
        fun addOnPlayCountListener(life: ILifeObject?, onPlayCountListener: OnPlayCountListener)
        fun addOnPlayLifeListener(life: ILifeObject?, onPlayLifeListener: OnPlayLifeListener)
        fun addOnReadyListener(life: ILifeObject?, onReadyListener: OnReadyListener)
        fun addOnPlayMomentListener(life: ILifeObject?, onPlayMomentListener: OnPlayMomentListener)
    }


}