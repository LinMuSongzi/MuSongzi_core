package com.musongzi.core.base.manager

import android.util.SparseArray

/*** created by linhui
 * 管理 单例的一个管理者服务集合
 *
 * * on 2022/7/29 */
internal class ManagerService : IManagerService {

    private var isReady = false
    private var managers = SparseArray<InstanceManager>();

    companion object {
        private val MANAGER = ManagerService()

        fun getInstance(): IManagerService {
            return MANAGER
        }

        const val ID = 0
    }

    override fun managerId() = ID

    override fun onReady(any: Any?) {
        if (!isReady && any is Int && any.and(InstanceManager.COMPLETE) > 0) {
            isReady = true
        }
    }

    override fun <I> getManager(id: Int): I? {
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
        val instanceManagers = SparseArray<InstanceManager>()
        for (m in managers) {
            val instanceManager = if (m.name() != null) {
                classLoader.loadClass(m.name()).newInstance() as InstanceManager
            } else {
                m.instance() as InstanceManager
            }
            instanceManager.onReady(m.readyNow(instanceManager))
            instanceManagers.put(instanceManager.managerId(), instanceManager)
        }
        onReady(ID.or(InstanceManager.COMPLETE))
    }




}