package com.musongzi.core.base.client

import com.musongzi.core.base.page2.IChangeDefaulState
import com.musongzi.core.base.page2.IPageDefualtViewModel
import com.musongzi.core.itf.IClient

interface IDefualtListClient : IClient, IChangeDefaulState {

    fun getPageDefualtViewModel(): IPageDefualtViewModel

}