package com.musongzi.core.base.page2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.musongzi.core.ExtensionCoreMethod.thisInstance
import com.musongzi.core.R
import com.musongzi.core.base.bean.DefaultShowInfo
import com.musongzi.core.base.business.EmptyBusiness
import com.musongzi.core.base.client.IDefualtListClient
import com.musongzi.core.base.page.ILayoutEmptyView
import com.musongzi.core.base.vm.DataDriveViewModel
import com.musongzi.core.itf.holder.IHolderLifecycle
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.IRead

/**
 * 管理缺省图
 */
class DefaultViewSupport<T> private constructor(
    private val provider: T, private var normalView: ILayoutEmptyView, private var read: IRead?
) : IDefualtListClient, IDefaultView where T : IHolderViewModelProvider, T : IHolderLifecycle {

    /**
     * 存储着当前的state
     */
    private var mDefualtHelpViewModel: DefualtHelpViewModel

    private val observerSet :Observer<Int>

    override var defaultState = EMPTY_STATE


    companion object {

        internal fun <T> createInstance(
            read: IRead, provider: T, viewGroup: ILayoutEmptyView, layoutInflater: LayoutInflater?
        ): DefaultViewSupport<T> where T : IHolderViewModelProvider, T : IHolderLifecycle {
            return DefaultViewSupport(provider, viewGroup, read)
        }

        const val UNCONNECT_STATE = 19
        const val EMPTY_STATE = 20
        const val LODING_STATE = 21
        const val NORMAL_STATE = 22
        const val DATA_STATE = 23


        const val UNCONNECT_ACTION = 200

        private const val TAG = "DefaultViewSupport"

    }

    private fun checkIsNeedUpdate() {
        val s = mDefualtHelpViewModel.state
        if (s != -1) {
            mDefualtHelpViewModel.state = s
            Log.d(TAG, "checkIsNeedUpdate: 需要更新state")
        } else {
            Log.d(TAG, "checkIsNeedUpdate: 无需更新state")
        }
    }

    /**
     * 默认显示缺省图
     */
    override var buildNormalDefault: (() -> DefaultShowInfo?)? = null
        set(value) {
            field = value
            checkIsNeedUpdate()
        }

    /**
     * 未连接缺省图
     */
    override var buildUnConnectDefault: (() -> DefaultShowInfo?)? = {
        DefaultShowInfo(
            state = UNCONNECT_STATE,
            centerRes = intArrayOf(R.mipmap.ic_default_un_connect_day, R.mipmap.ic_default_un_connect_dark),
            tip = "没有网络，请检查网络设置",
            tipColor = intArrayOf(R.color.BL5_30, R.color.BL1_30),
            buttonClickAction = 200,
            buttonColor = intArrayOf(R.color.BL5, R.color.BL1_60),
            buttonText = "",
            buttonBg = null
        )
    }
        set(value) {
            field = value
            checkIsNeedUpdate()
        }

    /**
     * 加载中缺省图
     */
    override var buildLoadingDefault: (() -> DefaultShowInfo?)? = null
        set(value) {
            field = value
            checkIsNeedUpdate()
        }

    /**
     * 数据为空缺省图
     */
    override var buildEmptyDefault: (() -> DefaultShowInfo?)? = {
        DefaultShowInfo(
            state = EMPTY_STATE, centerRes = intArrayOf(R.mipmap.empty_list_light, R.mipmap.empty_list_dark)
        )
    }
        set(value) {
            field = value
            checkIsNeedUpdate()
        }

    init {
        mDefualtHelpViewModel = DefualtHelpViewModel::class.java.thisInstance(provider)!!
        observerSet = Observer<Int> {
            it?.apply {
                var acton = this

                normalView.idGoTv.visibility = View.GONE
                val b = when (acton) {
                    UNCONNECT_STATE -> {
                        buildUnConnectDefault?.invoke()
                    }
                    EMPTY_STATE -> {
                        buildEmptyDefault?.invoke()
                    }
                    LODING_STATE -> {
                        buildLoadingDefault?.invoke()
                    }
                    NORMAL_STATE -> {
                        buildNormalDefault?.invoke()
                    }
                    else -> {
                        null
                    }
                }
                b?.apply bean@{
                    normalView.bean = this
                    provider.runOnUiThread {
                        mDefualtHelpViewModel.info = this@bean
                    }
                }

                checkActionOnClick()


            }
        }
        provider.getThisLifecycle()?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                changeDefualtState(defaultState)
                mDefualtHelpViewModel.observerState(owner, observerSet)
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    class DefualtHelpViewModel : DataDriveViewModel<EmptyBusiness>(), IPageDefualtViewModel

    override fun changeDefualtState(new: Int) {
        var old = mDefualtHelpViewModel.state
        if (!NetUtils.isConnected()) {
            Log.i(TAG, "changeDefualtState: 1")
            mDefualtHelpViewModel.state = UNCONNECT_STATE
            return
        }
        if (old == new) {
            Log.i(TAG, "changeDefualtState: 2")
            return
        }else{
            Log.i(TAG, "changeDefualtState: 3")
            old = new
        }
        mDefualtHelpViewModel.state = old
    }

    override fun getPageDefualtViewModel(): IPageDefualtViewModel {
        return mDefualtHelpViewModel
    }

    override fun showDialog(msg: String?) {

    }

    override fun disimissDialog() {

    }


    private fun checkActionOnClick() {


        when (normalView.bean?.state) {
            UNCONNECT_ACTION -> {
                normalView.idGoTv.setOnClickListener {
                    read?.refresh()
                }
            }
        }

        viewVisibility(normalView.idEmptyContentTv, !normalView.bean?.tip.isNullOrEmpty())
        viewVisibility(normalView.idGoTv, !normalView.bean?.buttonText.isNullOrEmpty())


    }

    override fun normalView(): ViewGroup = normalView.idContentLayout


}