package com.musongzi.test.vm

import com.musongzi.comment.viewmodel.SearchViewModel
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.test.MszTestApi

/*** created by linhui * on 2022/7/21 */
class MySearchViewModel: SearchViewModel<MszTestApi>() {

    override fun searchText(text: CharSequence) {
//        getHolderClient()?.showDialog()
//        getApi().searchText(text).sub{
//            client?.disimissDialog()
//        }
    }


}