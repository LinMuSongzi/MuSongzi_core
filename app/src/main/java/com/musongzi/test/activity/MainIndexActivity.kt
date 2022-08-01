package com.musongzi.test.activity

import android.os.Bundle
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.music.impl.Factory
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.test.business.MusicConfigHelpBusines

/*** created by linhui * on 2022/7/20 */
class MainIndexActivity : NormalFragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ManagerUtil.init(Array(1) {
            Factory.buildInstanceManagerHelp {
                MusicConfigHelpBusines()
            }
        }, classLoader)

    }

}