package com.musongzi.comment.business

import android.widget.EditText
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.ExtensionMethod.search
import com.musongzi.core.itf.vm.ISearchViewModel
import com.musongzi.core.base.business.BaseMapBusiness

/*** created by linhui * on 2022/7/21 */
class SearchBusiness : BaseMapBusiness<ISearchViewModel>() {

    override fun afterHandlerBusiness() {
        super.afterHandlerBusiness()
        SEARCH_STR_KEY.liveSaveStateObserver<String>(iAgent){
            iAgent.searchText(it)
        }
    }

    fun searchObserver(textView: EditText) {
        textView.search {
            SEARCH_STR_KEY.saveStateChange(iAgent, it)
        }
    }


    companion object {
        const val SEARCH_STR_KEY = "ssk"
    }


}