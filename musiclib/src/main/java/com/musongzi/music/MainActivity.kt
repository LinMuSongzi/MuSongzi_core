package com.musongzi.music

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.base.manager.ManagerService
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.bean.MediaPlayInfo
import com.musongzi.music.bean.MusicPlayInfo
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.music.itf.PlayMusicObervser
import com.musongzi.music.itf.small.*
import kotlinx.android.synthetic.main.msz_title_layout.view.*

class MainActivity : NormalFragmentActivity(),ILifeObject {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPlayQueue().getPlayController().playMusic("")
        getPlayQueue().getListenerManager(IPlayQueueManager.ON_COMPLETE).addOnPlayLifeListener(this,object :OnPlayLifeListener{
            override fun onStart(info: MediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun onStop(info: MediaPlayInfo) {
                TODO("Not yet implemented")
            }

            override fun onPause(info: MediaPlayInfo) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun getPlayQueue():IPlayQueueManager{
       return ManagerService.MANAGER.getManager<IPlayQueueManager>(IPlayQueueManager.MANAGER_ID)!!
    }

    override fun getThisLifecycle(): LifecycleOwner {
       return this
    }

}
