package com.mszsupport.itf

import android.app.Activity

/*** created by linhui * on 2022/8/16 */
interface IActivityViewInstance: IContextViewInstance {

    override fun getHolderContext(): Activity?

}