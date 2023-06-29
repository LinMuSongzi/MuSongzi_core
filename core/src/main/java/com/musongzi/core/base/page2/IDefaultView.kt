package com.musongzi.core.base.page2

import android.view.ViewGroup
import com.musongzi.core.base.bean.DefaultShowInfo


interface IDefaultView : IChangeDefaulState {

    fun normalView(): ViewGroup?

    var defaultState:Int


    var buildNormalDefault: (() -> DefaultShowInfo?)?
    var buildUnConnectDefault: (() -> DefaultShowInfo?)?
    var buildLoadingDefault: (() -> DefaultShowInfo?)?
    var buildEmptyDefault: (() -> DefaultShowInfo?)?

}