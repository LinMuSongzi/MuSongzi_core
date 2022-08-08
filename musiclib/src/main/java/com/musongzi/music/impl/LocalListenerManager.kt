package com.musongzi.music.impl

import androidx.lifecycle.LifecycleObserver
import com.musongzi.core.ExtensionCoreMethod.exceptionRun
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.PlayMusicObervser
import com.musongzi.music.itf.small.*

/*** created by linhui * on 2022/7/29 */
class LocalListenerManager : IPlayQueueManager.ListenerManager, PlayMusicObervser {

    val mOnPlayCompleteListeners = HashSet<OnPlayCompleteListener>()
    val mOnPlayCountListeners = HashSet<OnPlayCountListener>()
    val mOnPlayLifeListeners = HashSet<OnPlayLifeListener>()
    val mOnReadyListeners = HashSet<OnReadyListener>()
    val mOnPlayChangeListeners = HashSet<OnPlayMomentListener>()

    override fun addOnPlayCompleteListener(
        life: ILifeObject?,
        onPlayCompleteListener: OnPlayCompleteListener
    ) {
        observer(life, PlayQueueManagerImpl.MusicObserver(onPlayCompleteListener))
    }

    override fun addOnPlayCountListener(
        life: ILifeObject?,
        onPlayCountListener: OnPlayCountListener
    ) {
        observer(life, PlayQueueManagerImpl.MusicObserver(onPlayCountListener))
    }

    override fun addOnPlayLifeListener(life: ILifeObject?, onPlayLifeListener: OnPlayLifeListener) {
        observer(life, PlayQueueManagerImpl.MusicObserver(onPlayLifeListener))
    }

    override fun addOnReadyListener(life: ILifeObject?, onReadyListener: OnReadyListener) {
        observer(life, PlayQueueManagerImpl.MusicObserver(onReadyListener))
    }

    override fun addOnPlayMomentListener(
        life: ILifeObject?,
        onPlayMomentListener: OnPlayMomentListener
    ) {
        observer(life, PlayQueueManagerImpl.MusicObserver(onPlayMomentListener))
    }

    private fun observer(life: ILifeObject?, musicObserver: LifecycleObserver): Boolean {
        return life?.getThisLifecycle()?.let {
            it.lifecycle.addObserver(musicObserver)
            true
        } ?: false
    }

    override fun onStateChange(state: String, info: IMediaPlayInfo) {
        when (state) {
            IPlayController.ON_COMPLETE -> {
                for (listener in mOnPlayCompleteListeners) {
                    exceptionRun {
                        listener.onComplete(info = info)
                    }
                }
            }

            IPlayController.ON_START -> {
                for (listener in mOnPlayLifeListeners) {
                    exceptionRun {
                        listener.onStart(info = info)
                    }

                }
            }

            IPlayController.ON_PAUSE -> {
                for (listener in mOnPlayLifeListeners) {
                    exceptionRun {
                        listener.onPause(info = info)
                    }
                }
            }

            IPlayController.ON_STOP -> {
                for (listener in mOnPlayLifeListeners) {
                    exceptionRun {
                        listener.onStop(info = info)
                    }
                }
            }

            IPlayController.ON_BUFFER -> {
                for (listener in mOnPlayChangeListeners) {
                    exceptionRun {
                        listener.onBuffer(info = info,0,null)
                    }
                }
            }

            IPlayController.ON_PLAYING -> {
                for (listener in mOnPlayChangeListeners) {
                    exceptionRun {
                        listener.onPlaying(info = info)
                    }
                }
            }
        }
    }


}