package com.musongzi.core.base.manager

/*** created by linhui * on 2022/7/29 */
interface InstanceManager {

    fun managerId():Int

    fun onReady(a:Any?)

    companion object
    {
        const val COMPLETE = 1.shl(25)
    }


}