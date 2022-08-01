package com.musongzi.music

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.base.manager.ManagerUtil.getHolderManager
import com.musongzi.core.itf.ILifeObject
import com.musongzi.music.impl.Factory
import com.musongzi.music.itf.IPlayQueueManager

class MainActivity : NormalFragmentActivity(), ILifeObject {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        getPlayQueue().getPlayController().playMusic("")
//        getPlayQueue().getListenerManager(IPlayController.ON_COMPLETE).addOnPlayLifeListener(this,object :OnPlayLifeListener{
//            override fun onStart(info: MediaPlayInfo) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onStop(info: MediaPlayInfo) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onPause(info: MediaPlayInfo) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }


    private fun getPlayQueue(): IPlayQueueManager {
        return getHolderManager<IPlayQueueManager>(IPlayQueueManager.MANAGER_ID)!!
    }

    override fun getThisLifecycle(): LifecycleOwner {
        return this
    }


}
