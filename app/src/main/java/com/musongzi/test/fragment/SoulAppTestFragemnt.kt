package com.musongzi.test.fragment

import androidx.recyclerview.widget.RecyclerView
import com.musongzi.comment.databinding.AdapterModelCard1Binding
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.getApi
import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.client.IRefreshViewClient
import com.musongzi.core.base.fragment.QuickCollectionFragment
import com.musongzi.core.itf.page.ISource
import com.musongzi.test.MszTestApi
import com.musongzi.test.MyApplication.Companion.URL2
import com.musongzi.test.bean.ResponeCodeBean
import com.musongzi.test.databinding.FragmentSoulAppTestBinding
import com.psyone.sox.SoxProgramHandler
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.Observable

/*** created by linhui * on 2022/10/17 */
class SoulAppTestFragemnt :
    QuickCollectionFragment<FragmentSoulAppTestBinding, StringChooseBean, ResponeCodeBean<List<StringChooseBean>>>() {


    init {
        lifecycle
    }


    override fun createRecycleViewClient(): IRefreshViewClient {
        return object : IRefreshViewClient {
            override fun recycleView(): RecyclerView? {
                return dataBinding.idRecyclerView
            }

            override fun refreshView(): SmartRefreshLayout? {
                return dataBinding.idSmartRefreshLayout
            }
        }
    }

    override fun transformDataToList(entity: ResponeCodeBean<List<StringChooseBean>>): List<StringChooseBean> =
        entity.data

    override fun getRemoteData(index: Int): Observable<ResponeCodeBean<List<StringChooseBean>>>? =
        MszTestApi::class.java.getApi(this)?.getArrayEngine(index, getPageEngine()?.pageSize())

    override fun getAdapter(page: ISource<StringChooseBean>?): RecyclerView.Adapter<*>? =
        page?.adapter(AdapterModelCard1Binding::class.java){d,i,p->


            d.idContent2Iv.setOnClickListener {
                SoxProgramHandler.exoPlaySImple(requireContext(),this,"${URL2}wavTest2.mp3")
            }

        }

}