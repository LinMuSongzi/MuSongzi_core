package com.musongzi.core.base.manager

/*** created by linhui * on 2022/8/1 */
interface ManagerInstanceHelp {

    fun instance(): InstanceManager?

    fun name(): String? = null

    fun readyNow(my: InstanceManager): Any
}