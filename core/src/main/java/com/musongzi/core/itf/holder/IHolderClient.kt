package com.musongzi.core.itf.holder

import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IDialogSupport

interface IHolderClient<C :IClient?> {

    fun getHolderClient():C?

}