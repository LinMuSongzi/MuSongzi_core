package com.musongzi.core.base.business.collection

import com.musongzi.core.base.vm.IRefreshViewModel

object ViewListPageFactory {


    const val INFO_KEY = "info_key"

    @JvmStatic
    @Throws(Exception::class)
    fun create(name: String?, dictionary: IRefreshViewModel<*>): IHolderCollections {
        val viewPageEngine = dictionary.javaClass.classLoader!!.loadClass(name).newInstance() as IHolderCollections
        viewPageEngine.init(dictionary)
        return viewPageEngine
    }


}