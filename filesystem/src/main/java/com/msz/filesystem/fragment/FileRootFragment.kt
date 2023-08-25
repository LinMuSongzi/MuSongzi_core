package com.msz.filesystem.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.msz.filesystem.api.FileApi
import com.msz.filesystem.bean.DiskInfoI
import com.msz.filesystem.bean.IFile.Companion.ROOT
import com.msz.filesystem.bean.IFile.Companion.startDir
import com.msz.filesystem.databinding.FragmentRootFilesBinding
import com.msz.filesystem.databinding.ItemDiskBinding
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.gridLayoutManager
import com.musongzi.core.ExtensionCoreMethod.refreshLayoutInit
import com.musongzi.core.ExtensionCoreMethod.sub
import com.musongzi.core.base.fragment.DataBindingFragment
import com.musongzi.core.base.manager.RetrofitManager

class FileRootFragment : DataBindingFragment<FragmentRootFilesBinding>() {

    private val mDiskInfos by lazy {
        SourceImpl<DiskInfoI>()
    }

    override fun initView() {

        dataBinding.idSmartRefreshLayout.refreshLayoutInit(refresh = {
            loadRemote()
        }, isEnableLoadMore = false)

        dataBinding.idRecyclerView.gridLayoutManager(2, GridLayoutManager.VERTICAL) {
            mDiskInfos.adapter(ItemDiskBinding::class.java) { d, i, _ ->
                d.root.setOnClickListener {
                    i.startDir()
                }
            }
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
        loadRemote()
    }
}