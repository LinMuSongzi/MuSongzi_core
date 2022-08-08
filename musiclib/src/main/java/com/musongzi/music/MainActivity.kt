package com.musongzi.music

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.itf.IMediaPlayInfo
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.IPlayQueueManager.Companion.addOnPlayMomentListener
import com.musongzi.music.itf.IPlayQueueManager.Companion.addOnPlayCountListener
import com.musongzi.music.itf.IPlayQueueManager.Companion.addOnPlayLifeListener
import com.musongzi.music.itf.small.OnPlayCountListener
import com.musongzi.music.itf.small.OnPlayLifeListener
import com.musongzi.music.itf.small.OnPlayMomentListener

class MainActivity : NormalFragmentActivity(), ILifeObject {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        IPlayQueueManager::class.addOnPlayMomentListener(this,object :OnPlayMomentListener{
            override fun onBuffer(info: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun onPlaying(info: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

        })
        IPlayQueueManager::class.addOnPlayLifeListener(this,object :OnPlayLifeListener{
            override fun onStart(info: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun onStop(info: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun onPause(info: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

        })
        IPlayQueueManager::class.addOnPlayCountListener(this,object :OnPlayCountListener{
            override fun onKeepPlay(pre: IMediaPlayInfo, now: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun repeat(musicPlayInfo: IMediaPlayInfo) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun getThisLifecycle(): LifecycleOwner {
        return this
    }


}
