package com.musongzi.test.engine

import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.ExtensionMethod.adapter
import com.musongzi.core.ExtensionMethod.getApi
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.test.Api
import com.musongzi.test.databinding.AdapterStringBinding
import io.reactivex.rxjava3.core.Observable
import kotlin.coroutines.EmptyCoroutineContext

@CollecttionsEngine(isEnableReFresh = true, isEnableLoadMore = true, isEnableEventBus = true)
class ArrayEngine : BaseMoreViewEngine<String, List<String>>() {

    override fun getRemoteDataReal(page: Int): Observable<List<String>> = getApi(Api::class.java).getArrayEngine(page)

    override fun myAdapter() = adapter(AdapterStringBinding::class.java)

    override fun transformDataToList(entity: List<String>) = entity

}