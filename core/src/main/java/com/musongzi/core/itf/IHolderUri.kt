package com.musongzi.core.itf

import android.net.Uri

/*** created by linhui * on 2022/8/1 */
interface IHolderUri {

    fun holderFlag():Int

    fun getHolderUri():Uri?

    fun getHolderSting():String?

}