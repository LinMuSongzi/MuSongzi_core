package com.musongzi.core.base.manager

import android.content.res.Resources
import android.util.Log
import android.util.SparseArray
import com.musongzi.core.ExtensionCoreMethod.exceptionRun
import com.musongzi.core.R
import com.musongzi.core.util.ActivityThreadHelp

/*** created by linhui
 * 管理 单例的一个管理者服务集合
 *
 * * on 2022/7/29 */
internal class ManagerService : IManagerService {

    private var isReady = false
    private var managers = HashMap<String, InstanceManager>();

    companion object {
        private const val MANAGER_ARRAY_NAME = "manager_instance"
        private val MANAGER = ManagerService()
        const val TAG = "InstanceManager"
        fun getInstance(): IManagerService {
            return MANAGER
        }

//        const val ID = 0
    }

//    override fun managerId() = ID

    override fun onReady(any: Any?) {
        if (!isReady) {
            isReady = true
        }
    }


    override fun <I> getManager(id: String): I? {
        return managers[id] as? I
    }

    /**
     * 加载所有管理者服务
     * 通过类名的方式
     */
    @Synchronized
    override fun loadManagers(managers: Collection<ManagerInstanceHelp>, classLoader: ClassLoader) {
        if (isReady) {
            return
        }
//        val instanceManagers = SparseArray<InstanceManager>()
        val set: HashSet<ManagerInstanceHelp> = if (managers is HashSet) {
            managers;
        } else {
            HashSet<ManagerInstanceHelp>().apply {
                addAll(managers)
            }
        }

        exceptionRun {
            loadXmlStringManager(set, classLoader)
        }
        for (m in set) {
            val instanceManager = if (m.name() != null) {
                classLoader.loadClass(m.name()).newInstance() as InstanceManager
            } else {
                m.instance() as InstanceManager
            }
            instanceManager.onReady(m.readyNow(instanceManager))
            this.managers[m.key()] = instanceManager
        }
        onReady(null)
    }

    private fun loadXmlStringManager(set: HashSet<ManagerInstanceHelp>, classLoader: ClassLoader) {
        val packageName = ActivityThreadHelp.getCurrentApplication().packageName

        val id = ActivityThreadHelp.getCurrentApplication().resources.getIdentifier(MANAGER_ARRAY_NAME,"array",packageName)
//        Log.i(TAG, "loadXmlStringManager: err id = $id , $packageName")
//        val clazzR = Class.forName("$packageName.R${'$'}array")
//        val field = clazzR.getDeclaredField(MANAGER_ARRAY_NAME)
//        val id = field.get(null) as Int
        val array = ActivityThreadHelp.getCurrentApplication().resources.getStringArray(id)
        for (clazzName in array) {
            try {

                val instance = classLoader.loadClass(clazzName).newInstance()
                set.add(instance as ManagerInstanceHelp)
                Log.i(TAG, "loadXmlStringManager: succed $clazzName")
            } catch (e: Exception) {
                Log.i(TAG, "loadXmlStringManager:  err   $clazzName")
                e.printStackTrace()
            }
        }
    }


}