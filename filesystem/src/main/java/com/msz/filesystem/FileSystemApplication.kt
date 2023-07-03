package com.msz.filesystem

import com.msz.filesystem.instance.RetrofitIntance
import com.musongzi.core.base.MszApplicaton
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp
import com.musongzi.core.base.manager.ManagerInstanceHelp.Companion.instanceOnReady

class FileSystemApplication: MszApplicaton() {



    override fun getManagers(): Array<ManagerInstanceHelp> {
       return  arrayOf(ManagerInstanceHelp.instanceHelp {
           RetrofitIntance()
       },instanceOnReady {
           ActivityLifeManager.getInstance()
       })
    }
}