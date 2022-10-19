package com.musongzi.core.base.manager

/*** created by linhui * on 2022/8/1 */
interface IManagerService : InstanceManager {

    fun loadManagers(managers: Collection<ManagerInstanceHelp>, classLoader: ClassLoader)

    fun <I> getManager(id: String): I?

}