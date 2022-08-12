package com.musongzi.music.impl

import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayerManager
import com.musongzi.music.itf.PlayMediaObervser

/*** created by linhui * on 2022/8/1 */
internal class AndroidPlayManager : IPlayerManager, MediaPlayer.OnInfoListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    lateinit var corePlayMusicObervser: PlayMediaObervser

    val dalayMillis = 500L

    val mediaPlayer: MediaPlayer = MediaPlayer().apply {
        setOnInfoListener(this@AndroidPlayManager)
        setOnCompletionListener(this@AndroidPlayManager)
        setOnBufferingUpdateListener(this@AndroidPlayManager)
    }

    val handler: Handler by lazy {
        val handlerThread = HandlerThread("AndroidPlay-Thread")
        handlerThread.start()
        Handler(handlerThread.looper)
    }

    private val checkRunnable = object : Runnable {
        override fun run() {
            if (mediaPlayer.isPlaying) {
                corePlayMusicObervser.onStateChange(
                    IPlayController.ON_PLAYING,
                    getThisPlayInfo(),
                    mediaPlayer
                )
                handler.postDelayed(this, dalayMillis)
            }
        }

    }

    override fun observerState(p: PlayMediaObervser) {
        corePlayMusicObervser = p;
    }

    override fun observerState(life: ILifeObject?, p: PlayMediaObervser) {
//        TODO("Not yet implemented")

    }

    override fun playMusicByInfo(entity: IMediaPlayInfo) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(entity.getHolderSting())
        mediaPlayer.prepareAsync()
    }

    override fun playMusic(stringUrl: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(stringUrl)
        mediaPlayer.prepareAsync()
    }

    override fun pauseMusic() {
        if(mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            corePlayMusicObervser.onStateChange(
                IPlayController.ON_PAUSE,
                getThisPlayInfo(),
                mediaPlayer
            )
        }
    }

    override fun stopMusic() {
        mediaPlayer.stop()
        corePlayMusicObervser.onStateChange(
            IPlayController.ON_STOP,
            getThisPlayInfo(),
            mediaPlayer
        )
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        corePlayMusicObervser.onStateChange(
            IPlayController.ON_INFO,
            getThisPlayInfo(),
            mp,
            what to extra
        )

        mediaPlayer.start()
        corePlayMusicObervser.onStateChange(
            IPlayController.ON_START,
            getThisPlayInfo(),
            mediaPlayer
        )
        handler.removeCallbacksAndMessages(null)
        handler.post(checkRunnable)
        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        corePlayMusicObervser.onStateChange(IPlayController.ON_COMPLETE, getThisPlayInfo(), mp)
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        corePlayMusicObervser.onStateChange(
            IPlayController.ON_BUFFER,
            getThisPlayInfo(),
            mp,
            percent
        )
    }

    fun getThisPlayInfo(): IMediaPlayInfo? {
        return PlayQueueManagerImpl.getInstance().getPlayingInfo()
    }

}