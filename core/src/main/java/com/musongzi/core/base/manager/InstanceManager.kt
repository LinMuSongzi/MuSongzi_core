package com.musongzi.core.base.manager

/*** created by linhui * on 2022/7/29 */
interface InstanceManager {

    fun managerId():Int = hashCode()

    fun onReady(a:Any?){}

    companion object
    {
        const val COMPLETE = 1.shl(25)
        const val NO_ID = 0;
    }


}