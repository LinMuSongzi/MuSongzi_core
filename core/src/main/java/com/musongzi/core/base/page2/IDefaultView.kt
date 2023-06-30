package com.musongzi.core.base.page2

import android.view.View
import android.view.ViewGroup
import com.musongzi.core.base.bean.DefaultShowInfo
import com.musongzi.core.itf.holder.IHolderView


interface IDefaultView<V:View> : IChangeDefaulState, IHolderView<V> {

    var defaultState:Int

    var buildNormalDefault: (() -> DefaultShowInfo?)?
    var buildUnConnectDefault: (() -> DefaultShowInfo?)?
    var buildLoadingDefault: (() -> DefaultShowInfo?)?
    var buildEmptyDefault: (() -> DefaultShowInfo?)?

}