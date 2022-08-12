package com.musongzi.music.itf

import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerUtil.manager
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.small.*
import kotlin.reflect.KClass

/*** created by linhui   * on 2022/7/28
 * 一个音乐队列管理者
 *
 * 可以管理一组音乐队列/专辑
 * 拥有队列的信息[IMusicArray] ， 和基于音乐信息[IMediaPlayInfo]的一组数据
 *
 * note：音乐播放逻辑;通过控制器来进行音乐播放和控制，并且会把音乐添加进去当前的音乐集合/专辑[IPlayController]
 * 控制器通过 [IPlayQueueManager.getPlayController] 获取
 * 获得的控制器是基于当前[IMusicArray]音乐队列的(实现类[MusicArrayImpl])
 * [com.musongzi.music.impl.MusicArrayImpl] 实现了 [com.musongzi.core.itf.page.IRead2]
 * 具备当前音乐队列/专辑的，
 * 上一首[com.musongzi.core.itf.page.IRead2.pre] ,
 * 下一首[com.musongzi.core.itf.page.IRead2.next],
 * 重新播放[com.musongzi.core.itf.page.IRead2.refresh] 基本功能
 *
 * 监听回调通过[IPlayQueueManager.getListenerManager] 添加基于状态的监听
 *
 *  */
interface IPlayQueueManager : IHolderPlayController, InstanceManager {

    /**
     * 获取当前播放器控制器
     * @return 返回的是基于当前音乐队列的控制器
     */
    override fun getPlayController(): IPlayController

    fun generatedConfigPlayQueues(config: Set<String>? = null)

    /**
     * 获取当前播放的音乐队列列表
     */
    fun getPlayingQueue(): IMusicArray<IMediaPlayInfo>?

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
        const val NORMAL_NAME = "NORMAL_NAME"
        fun KClass<IPlayQueueManager>.pauseMusic(){
            MANAGER_ID.manager<IPlayQueueManager>().getPlayController().pauseMusic()
        }

        fun KClass<IPlayQueueManager>.stopMusic(){
            MANAGER_ID.manager<IPlayQueueManager>().getPlayController().stopMusic()
        }

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

        fun KClass<IPlayQueueManager>.playObsavable():IPlayObservable{
            return MANAGER_ID.manager<ISmartPlayQueueManager>()
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