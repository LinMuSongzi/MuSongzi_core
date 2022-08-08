package com.musongzi.music.impl

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IPlayController
import com.musongzi.music.itf.IPlayerManager
import com.musongzi.music.itf.PlayMusicObervser

/*** created by linhui * on 2022/8/1 */
class ShamPlayManager : IPlayerManager {

    companion object {
        const val TAG = "FakerPlayManager"

        const val INIT_FLAG = 0
        const val START_FLAG = 1
        const val PAUSE_FLAG = START_FLAG.shl(1);
        const val STOP_FLAG = START_FLAG.shl(2);
    }

    var handler = Handler(Looper.getMainLooper())
    private lateinit var playMusicObervser: PlayMusicObervser
    val info = LockInfo()
    var playInfoNow: IMediaPlayInfo? = null
    var lastPlayInfo = playInfoNow


    override fun observerState(p: PlayMusicObervser) {
        playMusicObervser = object : PlayMusicObervser {
            override fun onStateChange(state: String, info: IMediaPlayInfo) {
//                handler.post {
                p.onStateChange(state, info)
//                }
            }
        }
    }

    override fun observerState(life: ILifeObject?, p: PlayMusicObervser) {
        PlayQueueManagerImpl.getInstance().observerState(life, p)
    }

    override fun playMusic(stringUrl: String) {

        val playInfoNow =
            PlayQueueManagerImpl.getInstance().partnerInstance.createMusicInfo(stringUrl)
        this.playInfoNow = playInfoNow
        playMusicByInfo(playInfoNow)
    }

    override fun playMusicByInfo(entity: IMediaPlayInfo) {
//        Log.i(TAG, "playMusic: 开始播放")
//        if (!isStateFlag(START_FLAG)) {
//            PlayQueueManagerImpl.getInstance().joinHistory(playInfoNow)
//            thread.start()
//        } else {
//
//        }
    }

    override fun pauseMusic() {
        if (info.flag.and(START_FLAG) > 0) {
            info.flag = PAUSE_FLAG
        }
    }

    override fun stopMusic() {
        if (info.flag.and(START_FLAG) > 0 || info.flag.and(PAUSE_FLAG) > 0) {
            info.flag = STOP_FLAG.or(PAUSE_FLAG)
        }
        lastPlayInfo = playInfoNow
        playInfoNow = null;
    }

    fun isStateFlag(flag: Int): Boolean {
        return info.flag.and(flag) > 0
    }

    class LockInfo {

        var flag = 0

    }

}