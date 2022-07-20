package com.musongzi.comment.business

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.R
import com.musongzi.comment.bean.ImageLoadBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.client.IMainIndexViewModel
import com.musongzi.comment.databinding.AdapterMainBottomItemBinding
import com.musongzi.comment.util.SourceImpl
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.linearLayoutManager
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.ScreenUtil
import com.musongzi.core.util.ScreenUtil.SCREEN_1_5_WDITH

/*** created by linhui * on 2022/7/20 */
open class MainBottomBusiness : BaseLifeBusiness<IMainIndexViewModel>() {


    private var source: ISource<SimpleCardInfo> = SourceImpl()


    fun buildDataBySize() {
        (source.realData() as ArrayList).addAll(normalMainArrayInfo(iAgent))
        iAgent.getHolderClient()?.getRecycleView()?.linearLayoutManager(LinearLayoutManager.HORIZONTAL) {
            source.adapter(AdapterMainBottomItemBinding::class.java, { d, _ ->
                if (source.realData().size > 4) {
                    d.root.layoutParams.width = SCREEN_1_5_WDITH
                } else {
                    d.root.layoutParams.width = ScreenUtil.getScreenWidth() / source.realData().size
                }
            }) { d, i, p ->
                d.root.setOnClickListener {
                    i.onClick.invoke(it)
                }
            }
        }

    }

    private fun String.buildInfo(
        colors: Pair<Int, Int>,
        images: Pair<Any, Any>,
        onClick: (View) -> Unit,
    ): SimpleCardInfo {
        return SimpleCardInfo(
            this,
            colors,
            ImageLoadBean(images.first) to ImageLoadBean(images.second),
            onClick
        )
    }

    protected open fun normalMainArrayInfo(holderSavedStateHandle: IHolderSavedStateHandle): Array<SimpleCardInfo> {
        return arrayOf(
            "推荐".buildInfo(
                Color.parseColor("#666666") to Color.BLACK,
                R.mipmap.ic_empty_data to R.mipmap.ic_launcher
            )
            {
                INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, _1_INDEX)
            },
            "关注".buildInfo(
                Color.parseColor("#666666") to Color.BLACK,
                R.mipmap.ic_empty_data to R.mipmap.ic_launcher
            )
            {
                INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, _2_INDEX)
            },
            "活动".buildInfo(
                Color.parseColor("#666666") to Color.BLACK,
                R.mipmap.ic_empty_data to R.mipmap.ic_launcher
            )
            {
                INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, _3_INDEX)
            },
            "我的".buildInfo(
                Color.parseColor("#666666") to Color.BLACK,
                R.mipmap.ic_empty_data to R.mipmap.ic_launcher
            )
            {
                INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, _4_INDEX)
            }

        )
    }

    companion object {

        const val INDEX_CLICK_SAVED_KEY = "mbv_index"

        const val _1_INDEX = 0;
        const val _2_INDEX = 1;
        const val _3_INDEX = 2;
        const val _4_INDEX = 3;



    }

}