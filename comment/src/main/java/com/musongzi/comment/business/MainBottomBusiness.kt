package com.musongzi.comment.business

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.musongzi.comment.ExtensionMethod.getSaveStateValue
import com.musongzi.comment.ExtensionMethod.liveSaveStateObserver
import com.musongzi.comment.ExtensionMethod.saveStateChange
import com.musongzi.comment.R
import com.musongzi.comment.bean.ImageLoadBean
import com.musongzi.comment.bean.SimpleCardInfo
import com.musongzi.comment.business.itf.IMainIndexBusiness
import com.musongzi.comment.viewmodel.itf.IMainIndexViewModel
import com.musongzi.comment.databinding.AdapterMainBottomItemBinding
import com.musongzi.core.ExtensionCoreMethod.adapter
import com.musongzi.core.ExtensionCoreMethod.linearLayoutManager
import com.musongzi.core.ExtensionCoreMethod.wantPick
import com.musongzi.core.base.business.BaseLifeBusiness
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.util.ScreenUtil
import com.musongzi.core.util.ScreenUtil.SCREEN_1_5_WDITH

/*** created by linhui * on 2022/7/20 */
abstract class MainBottomBusiness : BaseLifeBusiness<IMainIndexViewModel>(), IMainIndexBusiness {

    override fun buildDataBySize() {
        val source = iAgent.getSource();
        if (source.realData().isEmpty()) {
            (source.realData() as ArrayList).addAll(normalMainArrayInfo(iAgent))
            val adapter = source.adapter(AdapterMainBottomItemBinding::class.java, { d, _ ->
                if (source.realData().size > sizeMax()) {
                    d.root.layoutParams.width = SCREEN_1_5_WDITH
                } else {
                    d.root.layoutParams.width = ScreenUtil.getScreenWidth() / source.realData().size
                }
            }) { d, i, p ->
                d.root.setOnClickListener {
                    i.onClick.invoke(it)
                }
            }
            iAgent.getHolderClient()?.getRecycleView()?.linearLayoutManager(LinearLayoutManager.HORIZONTAL) {
                    adapter
            }
            INDEX_CLICK_SAVED_KEY.liveSaveStateObserver<Int>(iAgent) {
                iAgent.wantPick().pickRun(source.realData()[it])
                adapter.notifyDataSetChanged()
            }
            handlerViewPageValues()
        }

        INDEX_CLICK_SAVED_KEY.saveStateChange(iAgent, 0)
    }

    private fun handlerViewPageValues() {
        val size = FRAGMENT_SZIE_KEY.getSaveStateValue<Int?>(iAgent)
        if (size == null) {
             val fragments = buildFragments()
            iAgent.getHolderClient()?.apply {
//                getViewpage2().bindAdapter(,iAgent.getThisLifecycle(),fragmentList = fragments)
            }
        }
    }

    protected abstract fun buildFragments(): List<Fragment>

    private fun sizeMax(): Byte {
        return 4
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
        val titleArray: Array<String> = buildTitle();
        val imageArray: Array<Pair<Any, Any>> = buildImage()
        val colorPair: Pair<Int, Int> = buildColorPair();
        val t = tatol()
        return Array(t) { index ->
            titleArray[index].buildInfo(
                colorPair,
                imageArray[index]
            ) {
                INDEX_CLICK_SAVED_KEY.saveStateChange(holderSavedStateHandle, index)
            }
        }
    }

    protected open fun buildImage(): Array<Pair<Any, Any>> {
        return arrayOf(
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher,
            R.mipmap.ic_empty_data to R.mipmap.ic_launcher
        )
    }

    protected open fun buildColorPair(): Pair<Int, Int> {
        return R.color.text_color_unSelect to R.color.text_color_select
    }

    protected open fun buildTitle(): Array<String> {
        return arrayOf("推荐", "关注", "活动", "我的")
    }

    protected open fun tatol() = 4

    companion object {

        const val FRAGMENT_SZIE_KEY = "f_size"

        const val INDEX_CLICK_SAVED_KEY = "mbv_index"
    }

}