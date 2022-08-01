package com.musongzi.music.impl

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.manager.ManagerUtil.getHolderManager
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.bean.MediaPlayInfo
import com.musongzi.music.bean.MusicPlayInfoImpl
import com.musongzi.music.itf.*
import com.musongzi.music.itf.small.*

/*** created by linhui * on 2022/7/28 */
internal class PlayQueueManagerImpl() :
    ISmartPlayQueueManager, PlayMusicObervser {


    private var observerStates = HashSet<PlayMusicObervser>()
    private lateinit var thsPlayingArray: Pair<IAttribute, IMusicArray>

    /**
     *  HashMap<String, Pair<IAttribute, IMusicArray>>
     *      String key
     *      Pair<IAttribute, IMusicArray> IAttribute  描述当前队列的信息的属性
     *                                    IMusicArray 音乐队列信息管理
     */
    private val holderMusicArrays = HashMap<String, Pair<IAttribute, IMusicArray>>()
    private val mLocalListenerManager = LocalListenerManager()
    lateinit var partnerInstance: IMusicInit
    private val playerManager: IPlayerManager by lazy {
        when (partnerInstance.buildPlayerManagerType()) {
            2 -> {
                partnerInstance.createPlayerManager(partnerInstance.buildPlayerManagerType())
            }
            else -> {
                Factory.createNativePlayer()
            }
        }
    }


    override fun playMusic(stringUrl: String, musicArray: IMusicArray) {
        if (thsPlayingArray.second != musicArray) {
            changePlayArray(stringUrl, musicArray);
        } else {
            val index = musicArray.realData().indexOf(stringUrl)
            if (index > 0) {
                musicArray.changeThisPlayIndex(index)
            } else {
                musicArray.changeThisPlayIndexAndAdd(stringUrl)
            }
        }
    }

    private fun changePlayArray(stringUrl: String, musicArray: IMusicArray) {
        holderMusicArrays[musicArray.attributeId]?.apply {
            thsPlayingArray = this
        }
    }

    override fun pauseMusic(musicArray: IMusicArray) {
        playerManager.pauseMusic()
    }

    override fun stopMusic(musicArray: IMusicArray) {
        playerManager.stopMusic()
    }

    override fun joinHistory(playInfoNow: IMediaPlayInfo?) {
        partnerInstance.observerOnPlayJoinHistory(playInfoNow)
    }

    override fun generatedConfigPlayQueues(config: Set<String>?) {
        val map = holderMusicArrays;
        val info = partnerInstance;
        val intstance = map[NORMAL_NAME]
        if (intstance == null) {
            val top = info.createArrayParent(NORMAL_NAME)
            val musicArray = info.createMusicArray(NORMAL_NAME)
            map[NORMAL_NAME] = top to musicArray
        }
        config?.let {
            for (v in it) {
                if (!it.contains(v)) {
                    map[v] = info.createArrayParent(v) to info.createMusicArray(NORMAL_NAME)
                }
            }
        }
    }

    override fun getPlayingQueue(): IMusicArray? {
        return thsPlayingArray?.second
    }

    override fun getMusicQueueByMusicItem(info: MusicPlayInfoImpl): Set<IMusicArray> {
        TODO("Not yet implemented")
    }

    override fun getPlayingQueueName(): String? = thsPlayingArray?.first?.attributeId

    override fun getPlayingInfo(): MediaPlayInfo {
        val i = thsPlayingArray.second;
        return i.realData()[i.thisPlayIndex()]
    }

    override fun getListenerManager(onComplete: String): IPlayQueueManager.ListenerManager {
        return mLocalListenerManager
    }

    override fun managerId() = IPlayQueueManager.MANAGER_ID

    override fun onReady(a: Any) {
        partnerInstance = a as IMusicInit
        playerManager.observerState(this)
        val obsaverble = partnerInstance.onGeneratedConfigPlayQueues(0)
        if (obsaverble == null) {
            generatedConfigPlayQueues()
        } else {
            obsaverble.sub {
                generatedConfigPlayQueues(it)
            }
        }
    }

    override fun getPlayController(): IPlayController {
        return thsPlayingArray.second.getPlayController()!!
    }

    override fun observerState(life: ILifeObject?, p: PlayMusicObervser) {
        life?.getThisLifecycle()?.let {
            it.lifecycle.addObserver(MusicObserver(p))
        }
    }

    internal class MusicObserver(private val p: Any) : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            when (p) {
                is PlayMusicObervser -> {
                    getInstance().observerStates.add(p)
                }
                is OnPlayCompleteListener -> {
                    getInstance().mLocalListenerManager.mOnPlayCompleteListeners.add(p)
                }
                is OnPlayChangeListener -> {
                    getInstance().mLocalListenerManager.mOnPlayChangeListeners.add(p)
                }
                is OnPlayCountListener -> {
                    getInstance().mLocalListenerManager.mOnPlayCountListeners.add(p)
                }
                is OnPlayLifeListener -> {
                    getInstance().mLocalListenerManager.mOnPlayLifeListeners.add(p)
                }
                is OnReadyListener -> {
                    getInstance().mLocalListenerManager.mOnReadyListeners.add(p)
                }
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            val flag = when (p) {
                is PlayMusicObervser -> {
                    getInstance().observerStates.remove(p)
                }
                is OnPlayCompleteListener -> {
                    getInstance().mLocalListenerManager.mOnPlayCompleteListeners.remove(p)
                }
                is OnPlayChangeListener -> {
                    getInstance().mLocalListenerManager.mOnPlayChangeListeners.remove(p)
                }
                is OnPlayCountListener -> {
                    getInstance().mLocalListenerManager.mOnPlayCountListeners.remove(p)
                }
                is OnPlayLifeListener -> {
                    getInstance().mLocalListenerManager.mOnPlayLifeListeners.remove(p)
                }
                is OnReadyListener -> {
                    getInstance().mLocalListenerManager.mOnReadyListeners.remove(p)
                }
                else -> false
            }
            Log.i("MusicObserver", "onDestroy: flag = $flag")
        }
    }

    companion object {

        const val NORMAL_NAME = "NORMAL_NAME"

        internal fun getInstance(): PlayQueueManagerImpl {
            return getHolderManager<IPlayQueueManager>(IPlayQueueManager.MANAGER_ID) as PlayQueueManagerImpl
        }
    }

    override fun onStateChange(state: String, info: IMediaPlayInfo) {
        mLocalListenerManager.onStateChange(state, info)
    }

}