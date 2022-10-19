package com.musongzi.core.base.manager

import android.util.Log
import com.musongzi.core.ExtensionCoreMethod.exceptionRun
import com.musongzi.core.itf.IHolderLockObject
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.WriteTxt

/*** created by linhui
 * 管理 单例的一个管理者服务集合
 *
 * * on 2022/7/29 */
internal class ManagerService : IManagerService,IHolderLockObject {

    private var isReady = false
    private var managers = HashMap<String, InstanceManager>();
    private val lockObject = object :Object(){
        override fun finalize() {
            WriteTxt.wirte("\n time = ${System.currentTimeMillis()} finalize ManagerService \n")
        }
    }

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

    override fun loadManagers(managers: Collection<ManagerInstanceHelp>, classLoader: ClassLoader) {
        synchronized(lockObject) {
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
                try {
                    val instanceManager = if (m.classLoadPathName() != null) {
                        classLoader.loadClass(m.classLoadPathName())
                            .newInstance() as InstanceManager
                    } else {
                        m.instance() as InstanceManager
                    }
                    instanceManager.onReady(m.readyNow(instanceManager))
                    this.managers[m.key()] = instanceManager
                    Log.i(TAG, "loadManagers: loaded key = ${m.key()}")
                } catch (e: Exception) {
                    Log.i(TAG, "loadManagers: err load manager name = $m")
                    e.printStackTrace()
                }
            }
            onReady(null)
        }
    }

    /**
     * 加载主项目下的string name = [MANAGER_ARRAY_NAME]的数组
     * [MANAGER_ARRAY_NAME]所标识的是 [ManagerInstanceHelp] 接口继承类
     */
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

    override fun getHolderLockObject(): Any {
        return lockObject;
    }


}