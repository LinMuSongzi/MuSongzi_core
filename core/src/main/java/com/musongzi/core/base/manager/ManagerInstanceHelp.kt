package com.musongzi.core.base.manager

/*** created by linhui * on 2022/8/1 */
interface ManagerInstanceHelp {

    fun instance(): InstanceManager?

    fun name(): String? = null

    fun readyNow(my: InstanceManager): Any? = null


    companion object {

        fun instanceHelp(instance: () -> InstanceManager): ManagerInstanceHelp {
            return object : ManagerInstanceHelp {
                override fun instance(): InstanceManager? {
                    return instance.invoke()
                }

            }
        }


        fun instanceOnReady(runnable: Runnable): ManagerInstanceHelp {
            return object : ManagerInstanceHelp {
                override fun instance(): InstanceManager? {
                    return InstanceManagerSimple(runnable)
                }
            }
        }

    }


}