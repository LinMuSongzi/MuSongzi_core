package com.msz.filesystem.fragment

import android.view.ViewGroup.LayoutParams
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.msz.filesystem.api.FileApi
import com.msz.filesystem.bean.FileInfo
import com.msz.filesystem.bean.IFile.Companion.ROOT
import com.msz.filesystem.bean.IFile.Companion.startDir
import com.msz.filesystem.bean.IFile.Companion.startDirOrOther
import com.msz.filesystem.bean.IFile.Companion.startDirOrOtherBySource
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
import com.musongzi.core.util.ScreenUtil
import io.reactivex.rxjava3.core.Observable
import java.lang.ref.WeakReference

class DirListFragment : DataBindingFragment<FragmentRootFilesBinding>(), IRead {

    val dir: String
        get() {
            return arguments?.getString("dir")!!
        }

    var chooseBean: BaseChooseBean? = null

    private val pageLoader: IPageEngine<FileInfo> by lazy {
        PageLoader.createInstance(object : PageCallBack<FileInfo, RespondInfo<List<FileInfo>>> {
            override val thisLifecycle: LifecycleOwner? = this@DirListFragment

            override fun getRemoteData(page: Int, pageSize: Int): Observable<RespondInfo<List<FileInfo>>>? {
                return RetrofitManager.getInstance().getApi(FileApi::class.java, this@DirListFragment).getDirFiles(ROOT, dir)
            }

            override fun transformDataToList(entity: RespondInfo<List<FileInfo>>?): List<FileInfo> {
                return entity?.data ?: mutableListOf()
            }

            override fun handlerDataChange(data: MutableList<FileInfo>, request: RequestObservableBean<RespondInfo<List<FileInfo>>>) {
                notifyDataSetChanged()
            }

        })
    }

    override fun initView() {

        dataBinding.idSmartRefreshLayout.refreshLayoutInit({
            refresh()
        }, isEnableLoadMore = false)
        val w = (ScreenUtil.getScreenWidth() - ScreenUtil.dp2px(15 * 4)) / 3
        dataBinding.idRecyclerView.gridLayoutManager(3) {
            pageLoader.adapter(ItemFiieBinding::class.java, { d, _ ->
                d.root.layoutParams.apply {
                    this.width = w
                }
                d.idFileIv.layoutParams.apply {
                    this.width = w - ScreenUtil.dp2px(30)
                    this.height = this.width
                }
            }) { d, i, _ ->
                chooseBean?.chooseFlag = chooseBean?.id_ == i.id_

                d.root.setOnClickListener {
                    chooseBean?.chooseFlag = false
                    chooseBean = i
                    chooseBean?.chooseFlag = true
                    val w = WeakReference(this@DirListFragment)
                    lifecycle.addObserver(object :DefaultLifecycleObserver{
                        override fun onResume(owner: LifecycleOwner) {
                            w.get()?.notifyDataSetChanged()
                            owner.lifecycle.removeObserver(this)
                        }
                    })
                    i.startDirOrOther(true, pageLoader.realData())
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