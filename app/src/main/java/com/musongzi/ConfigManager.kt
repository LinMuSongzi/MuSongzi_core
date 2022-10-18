package com.musongzi

import android.util.Log
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp

/*** created by linhui * on 2022/8/25 */
class ConfigManager : InstanceManager {
    fun showConfig() {

        Log.i(CONFIG_MANAGER, "showConfig: " + hashCode())
    }


    override fun onReady(a: Any?) {
        super.onReady(a)
        Log.i(CONFIG_MANAGER, "onReady: " + hashCode())
    }


    class ManagerInstanceHelpImpl : ManagerInstanceHelp {
        override fun instance(): InstanceManager? {
            return ConfigManager()
        }

        override fun key(): String {
            return CONFIG_MANAGER
        }
    }

}

const val CONFIG_MANAGER = "ConfigManager"