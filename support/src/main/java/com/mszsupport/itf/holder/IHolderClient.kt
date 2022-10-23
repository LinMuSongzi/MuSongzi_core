package com.mszsupport.itf.holder

import com.mszsupport.itf.IClient

interface IHolderClient<C : IClient?> {

    fun getHolderClient():C?

}