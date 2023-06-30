package com.musongzi.core.base.vm

import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.holder.IHolderApi

///*** created by linhui * on 2022/9/15 */
open class ApiViewModel<B : IBusiness> : DataDriveViewModel<B> {

    constructor() : super()
    constructor(saved: SavedStateHandle) : super(saved)


}