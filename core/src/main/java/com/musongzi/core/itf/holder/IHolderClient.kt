package com.musongzi.core.itf.holder

import com.musongzi.core.itf.IClient

interface IHolderClient<C : IClient?> {

    fun getHolderClient():C?

}