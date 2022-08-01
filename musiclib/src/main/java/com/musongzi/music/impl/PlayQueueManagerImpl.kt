package com.musongzi.music.impl

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerService
import com.musongzi.core.itf.IAttribute
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.bean.InstanceBean
import com.musongzi.music.bean.MediaPlayInfo
import com.musongzi.music.bean.MusicPlayInfo
import com.musongzi.music.impl.Factory.getMusicClassLoader
import com.musongzi.music.itf.*
import com.musongzi.music.itf.small.*
import kotlin.math.log

/*** created by linhui * on 2022/7/28 */
internal class PlayQueueManagerImpl() :
    ISmartPlayQueueManager, PlayMusicObervser {


    private var observerStates = HashSet<PlayMusicObervser>()
    private lateinit var thsPlayingArray: Pair<IAttribute, IMusicArray>
    private val holderMusicArrays = HashMap<String, Pair<IAttribute, IMusicArray>>()
    private val mLocalListenerManager = LocalListenerManager()
    private lateinit var instanceBean: InstanceBean
    private val playerManager: IPlayerManager by lazy {
        when (instanceBean.playerModel) {
            2 -> {
                TODO()
            }
            else -> {
                Factory.createNativePlayer()
            }
        }
    }


    override fun playMusic(stringUrl: String, musicArray: IMusicArray) {
        if (thsPlayingArray?.second != musicArray) {
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

    override fun generatedConfigPlayQueues(config: Set<String>?) {
        config?.apply {
            val busines = getMusicClassLoader().loadClass(instanceBean.instanceClassName)
                .newInstance() as IInstanceNext
            for (v in this) {
                if (!config.contains(v)) {
                    holderMusicArrays[v] = busines.create(
                        IAttribute::class.java,
                        v
                    ) to busines.create(IMusicArray::class.java, v)
                }
            }
        }
    }

    override fun getPlayingQueue(): IMusicArray? {
        return thsPlayingArray?.second
    }

    override fun getMusicQueueByMusicItem(info: MusicPlayInfo): Set<IMusicArray> {
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
        instanceBean = a as InstanceBean
        playerManager.observerState(this)
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
        internal fun getInstance(): PlayQueueManagerImpl {
            return ManagerService.MANAGER.getManager<IPlayQueueManager>(IPlayQueueManager.MANAGER_ID) as PlayQueueManagerImpl
        }
    }

    override fun onStateChange(state: String, info: MusicPlayInfo) {
        mLocalListenerManager.onStateChange(state,info)
    }

}