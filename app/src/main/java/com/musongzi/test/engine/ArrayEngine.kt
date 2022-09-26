package com.musongzi.test.engine

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.getApi
import com.musongzi.core.ExtensionCoreMethod.getNeedNext
import com.musongzi.core.StringChooseBean
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.HeadSupportByRecycleBusiness
import com.musongzi.core.base.fragment.BaseCollectionsViewFragment
import com.musongzi.core.base.fragment.BaseCollectionsViewFragment.Companion.TOTAL_KEY
import com.musongzi.core.base.vm.IRefreshViewModel
import com.musongzi.test.Api
import com.musongzi.test.databinding.AdapterStringBinding
import io.reactivex.rxjava3.core.Observable

@CollecttionsEngine(isEnableReFresh = true, isEnableLoadMore = true, isEnableEventBus = true)
class ArrayEngine : BaseMoreViewEngine<StringChooseBean, Array<StringChooseBean>>() {


    override fun onInitAfter(iRefreshViewModel: IRefreshViewModel<StringChooseBean>) {
        HeadSupportByRecycleBusiness::class.java.getNeedNext(this)?.apply {


        }


        TOTAL_KEY.liveSaveStateObserver<Int>(getRefreshViewModel()) {
            Log.i(TAG, "onInitAfter: 最大值是 $it")
        }
    }

    var sum = 0;

    override fun getRemoteDataReal(page: Int): Observable<Array<StringChooseBean>>? =
        Api::class.java.getApi(getRefreshViewModel().refreshHolderClient())?.getArrayEngine(page)
            .apply {
                Log.i(TAG, "getRemoteDataReal: load page = $page")
            }

//    override fun myAdapter() =
//        HeadSupportByRecycleBusiness::class.java.getNeedNext(this)!!.instanceAdapter(
//            this,
//            AdapterStringBinding::class.java
//        )
//        { d, i, p ->
//            Log.i(TAG, "myAdapter: position = $p")
//            pickSingle(d.root, i)
//        }

    override fun myAdapter():RecyclerView.Adapter<*> = adapter(AdapterStringBinding::class.java,{d,t->
        Log.i(TAG, "myAdapter: convert = t = ${++sum}")
    }) { d, i, p ->
        Log.i(TAG, "myAdapter: position = $p")
        pickSingle(d.root, i)
    }

    override fun transformDataToList(entity: Array<StringChooseBean>) =
        ArrayList<StringChooseBean>().let {
//            if(page() == thisStartPage()){
//
//            }
            it.addAll(entity)
            BaseCollectionsViewFragment.TOTAL_KEY.saveStateChange(
                getRefreshViewModel(),
                entity.size
            )
            it
        }

}