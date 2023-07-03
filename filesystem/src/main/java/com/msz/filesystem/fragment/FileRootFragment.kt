package com.msz.filesystem.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.msz.filesystem.bean.DiskInfo
import com.msz.filesystem.bean.RespondInfo
import com.msz.filesystem.databinding.FragmentRootFilesBinding
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.gridLayoutManager
import com.musongzi.core.base.fragment.DataBindingFragment
import com.musongzi.core.base.page2.PageCallBack
import com.musongzi.core.base.page2.PageLoader
import com.musongzi.core.base.page2.RequestObservableBean
import com.musongzi.core.itf.page.IPageEngine2
import io.reactivex.rxjava3.core.Observable

class FileRootFragment : DataBindingFragment<FragmentRootFilesBinding>() {

    lateinit var pageLoader : IPageEngine2<DiskInfo, RespondInfo<List<DiskInfo>>>

    override fun initView() {

        pageLoaderInit()

        dataBinding.idRecyclerView.gridLayoutManager(2,GridLayoutManager.VERTICAL) {
            pageLoader.adapter()
        }
    }

    private fun pageLoaderInit() {

        pageLoader = PageLoader.createInstance(object :PageCallBack<DiskInfo, RespondInfo<List<DiskInfo>>>{

            override val thisLifecycle: LifecycleOwner? = this@FileRootFragment

            override fun getRemoteData(page: Int, pageSize: Int): Observable<RespondInfo<List<DiskInfo>>>? {
                TODO("Not yet implemented")
            }

            override fun transformDataToList(entity: RespondInfo<List<DiskInfo>>?): List<DiskInfo> {
                return entity?.data ?: mutableListOf()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun handlerDataChange(data: MutableList<DiskInfo>, request: RequestObservableBean<RespondInfo<List<DiskInfo>>>) {
                dataBinding.idRecyclerView.adapter?.notifyDataSetChanged()
            }

        })
    }

    override fun initData() {

    }
}