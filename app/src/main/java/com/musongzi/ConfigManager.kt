package com.musongzi

import android.util.Log
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp

/*** created by linhui * on 2022/8/25 */
class ConfigManager :InstanceManager {


//    override fun onReady(a: Any?) {
//        super.onReady(a)
//
//    }


    class ManagerInstanceHelpImpl :ManagerInstanceHelp{
        override fun instance(): InstanceManager? {
            return ConfigManager()
        }
    }

}