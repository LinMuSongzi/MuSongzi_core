package com.musongzi.core.base.manager

/*** created by linhui * on 2022/8/1 */
object ManagerUtil {

    fun init(managers: Collection<ManagerInstanceHelp>, classLoader: ClassLoader) {
        ManagerService.getInstance().loadManagers(managers, classLoader)
    }

    fun getService(): InstanceManager {
        return ManagerService.getInstance()
    }

    fun <T> getHolderManager(id: String): T? {
        return ManagerService.getInstance().getManager(id)
    }

    fun <T> String.manager(): T {
        return getHolderManager(this)!!
    }

}