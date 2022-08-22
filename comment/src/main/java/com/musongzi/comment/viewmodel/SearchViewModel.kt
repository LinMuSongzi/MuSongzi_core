package com.musongzi.comment.viewmodel

import com.musongzi.comment.ExtensionMethod.getSaveStateValue
import com.musongzi.comment.business.SearchBusiness
import com.musongzi.core.itf.client.ISearchClient
import com.musongzi.core.itf.vm.ISearchViewModel

/*** created by linhui * on 2022/7/21 */
abstract class SearchViewModel<A> : ApiViewModel<ISearchClient, SearchBusiness, A>() ,ISearchViewModel{

    fun getSearchTest():String?{
       return SearchBusiness.SEARCH_STR_KEY.getSaveStateValue<String>(this)
    }

}