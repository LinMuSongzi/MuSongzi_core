package com.msz.filesystem.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.msz.filesystem.api.FileApi
import com.msz.filesystem.bean.DiskInfo
import com.msz.filesystem.bean.RespondInfo
import com.msz.filesystem.databinding.FragmentRootFilesBinding
import com.msz.filesystem.databinding.ItemDiskBinding
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.gridLayoutManager
import com.musongzi.core.ExtensionCoreMethod.refreshLayoutInit
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.fragment.DataBindingFragment
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.page2.PageCallBack
import com.musongzi.core.base.page2.PageLoader
import com.musongzi.core.base.page2.RequestObservableBean
import com.musongzi.core.itf.page.IPageEngine2
import io.reactivex.rxjava3.core.Observable

class FileRootFragment : DataBindingFragment<FragmentRootFilesBinding>() {
    companion object {
        const val ROOT = "root_msz"
        const val ROOT_TIME = -1L
    }

    private val mDiskInfos by lazy {
        SourceImpl<DiskInfo>()
    }

    override fun initView() {

        dataBinding.idSmartRefreshLayout.refreshLayoutInit(refresh = {
            loadRemote()
        }, isEnableLoadMore = false)

        dataBinding.idRecyclerView.gridLayoutManager(2, GridLayoutManager.VERTICAL) {
            mDiskInfos.adapter(ItemDiskBinding::class.java)
        }

    }

    private fun loadRemote() {


        RetrofitManager.getInstance().getApi(FileApi::class.java, this@FileRootFragment).getRoot(ROOT).sub {
            it.data?.apply {
                mDiskInfos.realData().clear()
                mDiskInfos.realData().addAll(this)
                dataBinding.idRecyclerView.adapter?.notifyDataSetChanged()
            }

        }
    }

    override fun initData() {
        dataBinding.idSmartRefreshLayout.autoRefresh(200)
    }
}