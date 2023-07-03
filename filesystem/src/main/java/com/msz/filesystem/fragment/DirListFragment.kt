package com.msz.filesystem.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.msz.filesystem.api.FileApi
import com.msz.filesystem.bean.FileInfoI
import com.msz.filesystem.bean.IFile.Companion.ROOT
import com.msz.filesystem.bean.IFile.Companion.startDir
import com.msz.filesystem.bean.RespondInfo
import com.msz.filesystem.databinding.FragmentRootFilesBinding
import com.msz.filesystem.databinding.ItemFiieBinding
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.gridLayoutManager
import com.musongzi.core.ExtensionCoreMethod.refreshLayoutInit
import com.musongzi.core.base.bean.BaseChooseBean
import com.musongzi.core.base.fragment.DataBindingFragment
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.page2.PageCallBack
import com.musongzi.core.base.page2.PageLoader
import com.musongzi.core.base.page2.RequestObservableBean
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.IRead
import io.reactivex.rxjava3.core.Observable

class DirListFragment : DataBindingFragment<FragmentRootFilesBinding>(), IRead {

    val dir: String
        get() {
            return arguments?.getString("dir")!!
        }

    var chooseBean: BaseChooseBean? = null

    private val pageLoader: IPageEngine<FileInfoI> by lazy {
        PageLoader.createInstance(object : PageCallBack<FileInfoI, RespondInfo<List<FileInfoI>>> {
            override val thisLifecycle: LifecycleOwner? = this@DirListFragment

            override fun getRemoteData(page: Int, pageSize: Int): Observable<RespondInfo<List<FileInfoI>>>? {
                return RetrofitManager.getInstance().getApi(FileApi::class.java, this@DirListFragment).getDirFiles(ROOT, dir)
            }

            override fun transformDataToList(entity: RespondInfo<List<FileInfoI>>?): List<FileInfoI> {
                return entity?.data ?: mutableListOf()
            }

            override fun handlerDataChange(data: MutableList<FileInfoI>, request: RequestObservableBean<RespondInfo<List<FileInfoI>>>) {
                notifyDataSetChanged()
            }

        })
    }

    override fun initView() {

        dataBinding.idSmartRefreshLayout.refreshLayoutInit({
            refresh()
        }, isEnableLoadMore = false)
        dataBinding.idRecyclerView.gridLayoutManager(4) {
            pageLoader.adapter(ItemFiieBinding::class.java) { d, i, p ->

                chooseBean?.chooseFlag = chooseBean?.id_ == i.id_

                d.root.setOnClickListener {
                    chooseBean?.chooseFlag = false
                    chooseBean = i
                    chooseBean?.chooseFlag = true
                    notifyDataSetChanged()
                    i.startDir()
                }
            }
        }
    }

    override fun notifyDataSetChanged() {
        dataBinding.idRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun initData() {
        pageLoader.refresh()
    }

    override fun refresh() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            pageLoader.refresh()
        }
    }

    override fun next() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            pageLoader.next()
        }
    }
}