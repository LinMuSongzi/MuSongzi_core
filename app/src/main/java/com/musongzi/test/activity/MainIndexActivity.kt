package com.musongzi.test.activity

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.base.business.itf.IHolderSupportActivityBusiness
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.holder.IHolderApi
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.InjectionHelp
import com.musongzi.music.impl.Factory
import com.musongzi.music.itf.IPlayQueueManager
import com.musongzi.test.business.MusicConfigHelpBusines

/*** created by linhui * on 2022/7/20 */
class MainIndexActivity : NormalFragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ManagerUtil.init(arrayListOf(Factory.buildInstanceManagerHelp {
            MusicConfigHelpBusines().apply {
                afterHandlerBusiness()
            }
        }), classLoader)


    }

}