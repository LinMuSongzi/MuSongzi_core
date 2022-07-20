package com.musongzi.test.engine

import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.getApi
import com.musongzi.core.StringChooseBean
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.test.Api
import com.musongzi.test.databinding.AdapterStringBinding
import io.reactivex.rxjava3.core.Observable

@CollecttionsEngine(isEnableReFresh = true, isEnableLoadMore = true, isEnableEventBus = true)
class ArrayEngine : BaseMoreViewEngine<StringChooseBean, Array<StringChooseBean>>() {


    override fun onInitAfter(iRefreshViewModel: IRefreshViewModel<StringChooseBean>) {

    }

    override fun getRemoteDataReal(page: Int): Observable<Array<StringChooseBean>> = getApi(Api::class.java).getArrayEngine(page)

    override fun myAdapter() = adapter(AdapterStringBinding::class.java){d,i,p->
        pickSingle(d.root,i)
    }

    override fun transformDataToList(entity: Array<StringChooseBean>) = ArrayList<StringChooseBean>().let{
        it.addAll(entity)
        it
    }

}