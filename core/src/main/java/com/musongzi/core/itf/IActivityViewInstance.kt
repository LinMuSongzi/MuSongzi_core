package com.musongzi.core.itf

import android.app.Activity
import android.content.Context

/*** created by linhui * on 2022/8/16 */
interface IActivityViewInstance:IContextViewInstance {

    override fun getHolderContext(): Activity?

}