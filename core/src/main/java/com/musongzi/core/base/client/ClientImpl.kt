package com.musongzi.core.base.client

import com.musongzi.core.itf.IClient

open class ClientImpl<T : IClient>(var base: T) : IClient {
    override fun showDialog(msg: String?) {
        base.showDialog(msg)
    }

    override fun disimissDialog() {
        base.disimissDialog()
    }

    override fun disconnect() = base.disconnect()

//    override fun getNextByClass(nextClass: Class<*>): IClient? = base.getNextByClass(nextClass)
}