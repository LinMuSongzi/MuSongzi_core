package com.musongzi.core.base.manager

import android.util.SparseArray

/*** created by linhui * on 2022/7/29 */
class ManagerService : InstanceManager {

    private var isReady = false
    private var managers = SparseArray<InstanceManager>();

    companion object {
        val MANAGER = ManagerService()
        const val ID = 0
    }

    override fun managerId() = ID

    override fun onReady(any: Any) {
        if (!isReady && any is Int && any.and(InstanceManager.COMPLETE) > 0) {
            isReady = true
        }
    }

    fun <I> getManager(id: Int): I? {
        return managers[id] as? I
    }

    @Synchronized
    fun loadManaers(
        managers: ManagerListBean,
        classLoader: ClassLoader,
        run: (instance: InstanceManager) -> Any
    ) {
        if (isReady) {
            return
        }
        val instanceManagers = SparseArray<InstanceManager>()
        for (m in managers.set) {
            val instanceManager = classLoader.loadClass(m).newInstance() as InstanceManager
            instanceManager.onReady(run.invoke(instanceManager))
            instanceManagers.put(instanceManager.managerId(), instanceManager)
        }
        onReady(ID.or(InstanceManager.COMPLETE))
    }

}