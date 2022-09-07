package com.musongzi.test.engine

import android.util.Log
import com.musongzi.comment.ExtensionMethod.instacne
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.getApi
import com.musongzi.core.StringChooseBean
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.fragment.BaseCollectionsViewFragment
import com.musongzi.core.base.fragment.BaseCollectionsViewFragment.Companion.TOTAL_KEY
import com.musongzi.core.base.fragment.CollectionsViewFragment
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.core.util.InjectionHelp
import com.musongzi.test.Api
import com.musongzi.test.databinding.AdapterStringBinding
import com.musongzi.test.vm.TestViewModel
import io.reactivex.rxjava3.core.Observable

@CollecttionsEngine(isEnableReFresh = true, isEnableLoadMore = true, isEnableEventBus = true)
class ArrayEngine : BaseMoreViewEngine<StringChooseBean, Array<StringChooseBean>>() {


    override fun onInitAfter(iRefreshViewModel: IRefreshViewModel<StringChooseBean>) {
//        TestViewModel::class.java.instacne(getRefreshViewModel().getHolderViewModelProvider()?.topViewModelProvider())
//        InjectionHelp.getViewModel<>()
//        getRefreshViewModel().getHolderViewModelProvider()?.topViewModelProvider().ine
//        "haha".saveStateChange(getRefreshViewModel(),1)
        TOTAL_KEY.liveSaveStateObserver<Int>(getRefreshViewModel()){
            Log.i(TAG, "onInitAfter: 最大值是 $it")
        }
    }

    override fun getRemoteDataReal(page: Int): Observable<Array<StringChooseBean>>? =
        Api::class.java.getApi(getRefreshViewModel().refreshHolderClient())?.getArrayEngine(page)

    override fun myAdapter() = adapter(AdapterStringBinding::class.java) { d, i, p ->
        pickSingle(d.root, i)
    }

    override fun transformDataToList(entity: Array<StringChooseBean>) =
        ArrayList<StringChooseBean>().let {
            it.addAll(entity)
            BaseCollectionsViewFragment.TOTAL_KEY.saveStateChange(getRefreshViewModel(),entity.size)
            it
        }

}