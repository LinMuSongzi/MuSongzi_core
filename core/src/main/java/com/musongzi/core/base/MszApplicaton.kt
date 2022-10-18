package com.musongzi.core.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.base.manager.UncatchExcetionManager
import com.musongzi.core.itf.IHolderApplication

/*** created by linhui * on 2022/8/25 */
abstract class MszApplicaton : MultiDexApplication(), IHolderApplication {

    companion object{
        const val UNCATCH_MANAGER = "UncatchExcetionHelp"
    }

    final override fun onCreate() {
        super.onCreate()
//        ManagerUtil.
        ManagerUtil.init(getManagers().let {
            val h = HashSet<ManagerInstanceHelp>()
            if(enableWriteException()){
                h.add(UncatchExcetionHelp())
            }
            h.addAll(it)
            h
        }, getManagerClassLoader())
    }

    protected fun enableWriteException(): Boolean {
        return true;
    }

    protected open fun getManagerClassLoader(): ClassLoader {
        return classLoader
    }

    protected abstract fun getManagers():Array<ManagerInstanceHelp>


    override fun getHolderContext(): Context {
        return this
    }

    internal class UncatchExcetionHelp :ManagerInstanceHelp{
        override fun instance(): InstanceManager? {
           return UncatchExcetionManager()
        }

        override fun key(): String {
            return UNCATCH_MANAGER
        }
    }

}