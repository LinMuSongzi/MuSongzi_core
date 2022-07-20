package com.musongzi.comment.business

import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.R
import com.musongzi.comment.bean.ImageLoadBean
import com.musongzi.comment.databinding.AdapterMainBottomItemBinding
import com.musongzi.comment.util.SourceImpl
import com.musongzi.comment.view.MainBottomView
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.linearLayoutManager
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.ScreenUtil
import com.musongzi.core.util.ScreenUtil.SCREEN_1_5_WDITH
import com.musongzi.core.view.IMainBottomView

/*** created by linhui * on 2022/7/20 */
class MainBottomBusiness : BaseMapBusiness<IMainBottomView>() {


    private var source: ISource<MainBottomView.Info> = SourceImpl()


    fun buildDataBySize(array: Array<MainBottomView.Info>) {
        (source.realData() as ArrayList).addAll(array)
        iAgent.getRecycleView().linearLayoutManager(LinearLayoutManager.HORIZONTAL) {
            source.adapter(AdapterMainBottomItemBinding::class.java, { d, _ ->
                if (array.size > 4) {
                    d.root.layoutParams.width = SCREEN_1_5_WDITH
                } else {
                    d.root.layoutParams.width = ScreenUtil.getScreenWidth() / array.size
                }
            }) { d, i, p ->
                d.root.setOnClickListener {
                    i.onClick.invoke(it)
                }
            }
        }

    }

    companion object {

        const val SAVED_KEY = "MainBottomView"

        const val _1_INDEX = 0;
        const val _2_INDEX = 1;
        const val _3_INDEX = 2;
        const val _4_INDEX = 3;

        fun String.buildInfo(
            colors: Pair<Int, Int>,
            images: Pair<Any, Any>,
            onClick: (View) -> Unit,
        ): MainBottomView.Info {
            return MainBottomView.Info(
                this,
                colors,
                ImageLoadBean(images.first) to ImageLoadBean(images.second),
                onClick
            )
        }

        fun narmalMainArrayInfo(holderSavedStateHandle: ILifeSaveStateHandle): Array<MainBottomView.Info> {
            return arrayOf(
                "推荐".buildInfo(
                    Color.parseColor("#666666") to Color.BLACK,
                    R.mipmap.ic_empty_data to R.mipmap.ic_launcher
                )
                {
                    SAVED_KEY.saveStateChange(holderSavedStateHandle, _1_INDEX)
                },
                "关注".buildInfo(
                    Color.parseColor("#666666") to Color.BLACK,
                    R.mipmap.ic_empty_data to R.mipmap.ic_launcher
                )
                {
                    SAVED_KEY.saveStateChange(holderSavedStateHandle, _2_INDEX)
                },
                "活动".buildInfo(
                    Color.parseColor("#666666") to Color.BLACK,
                    R.mipmap.ic_empty_data to R.mipmap.ic_launcher
                )
                {
                    SAVED_KEY.saveStateChange(holderSavedStateHandle, _3_INDEX)
                },
                "我的".buildInfo(
                    Color.parseColor("#666666") to Color.BLACK,
                    R.mipmap.ic_empty_data to R.mipmap.ic_launcher
                )
                {
                    SAVED_KEY.saveStateChange(holderSavedStateHandle, _4_INDEX)
                }

            )
        }

    }

}