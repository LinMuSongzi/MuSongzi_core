package com.musongzi.core

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musongzi.core.base.adapter.TypeSupportAdaper
import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.ActivityThreadHelp.getCurrentApplication
import com.musongzi.core.util.TextUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

object ExtensionMethod {

    @JvmStatic
    fun <T> T.layoutInflater(p: ViewGroup, res: Int) = LayoutInflater.from(getCurrentApplication()).inflate(res, p, false);

    fun <T> T.exceptionRun(run: () -> Unit) {
        try {
            run()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T, R> T.exceptionRunByReturn(run: () -> R): R? {
        try {
            return run()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null;
    }

    fun ViewDataBinding.businessSet(business: Any) {
        exceptionRun {
            javaClass.getMethod("setBusiness", business::class.java).invoke(this, business)
            Log.i("businessSet", ": succeed " + business.javaClass.simpleName)
        }
    }

    fun <T> ViewDataBinding.entitySet(entity: String, clazz: Class<T>, entityObject: T?) {
        exceptionRun {
            javaClass.getMethod("set${TextUtil.capitalizationText(entity)}", clazz).invoke(this, entityObject)
            Log.i("businessSet", ": succeed " + entity.javaClass.simpleName)
        }
    }

    @JvmStatic
    fun SmartRefreshLayout.refreshLayoutInit(p: IPageEngine<*>?, isEnableRefresh: Boolean, isEnableLoadMore: Boolean) {
        refreshLayoutInit(this, p, isEnableRefresh, isEnableLoadMore, 500)
    }
    @JvmStatic
    fun SmartRefreshLayout.refreshLayoutInit(p: IPageEngine<*>?) {
        refreshLayoutInit(this, p, true, true, 500)
    }

    @JvmStatic
    fun refreshLayoutInit(refreshLayout: SmartRefreshLayout, p: IPageEngine<*>?, isEnableRefresh: Boolean, isEnableLoadMore: Boolean, time: Int) {
        Log.i("refreshLayoutInit", ": $isEnableRefresh , $isEnableLoadMore")
        if (isEnableRefresh) {
            refreshLayout.setOnRefreshListener {
                it.finishRefresh(time)
                p?.refresh()
            }
        }
        if (isEnableLoadMore) {
            refreshLayout.setOnLoadMoreListener {
                it.finishLoadMore(time)
                p?.next()
            }
        }
        if (isEnableRefresh) {
            refreshLayout.setRefreshHeader(MaterialHeader(refreshLayout.context))
        }
        if (isEnableLoadMore) {
            refreshLayout.setRefreshFooter(ClassicsFooter(refreshLayout.context))
        }
        refreshLayout.setEnableRefresh(isEnableRefresh)
        refreshLayout.setEnableLoadMore(isEnableLoadMore)
        refreshLayout.setEnableAutoLoadMore(false);
    }

    fun <T : ViewDataBinding, R> R.dataBindingInflate(clazz: Class<T>, view: ViewGroup): T? {
        return exceptionRunByReturn {
            val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            method.invoke(null, LayoutInflater.from(getCurrentApplication()), view, false) as? T
        }
    }

//    fun <T> T.threadStart(r: Runnable) {
//        ThreadUtil.startThread(r)
//    }

    @JvmStatic
    fun <I, T : ISource<I>> T.adapter() = TypeSupportAdaper(this.realData())
//    fun <I, T : ISource<I>> T.adapter(v:RecyclerView) = {
//        TypeSupportAdaper(this.realData())
//    }

    /**
     * 返回一个adapter，不做任何数据绑定
     * @receiver T
     * @param c Class<D>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>) = adapter(c, null);

    /**
     * 返回一个adapter,有一个onBindView
     * @receiver T
     * @param c Class<D>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>?
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>, run: ((dataBinding: D, item: I, postion: Int) -> Unit)?) = TypeSupportAdaper.build(realData(), c, run
        ?: { _, _, _ -> })

    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>, run: ((dataBinding: D, item: I, postion: Int) -> Unit)?, isSetBean: Boolean?) = TypeSupportAdaper.build(realData(), c, run
        ?: { _, _, _ -> }, isSetBean)

    /**
     * 返回一个adapter ，有一个onCreatView，有一个onBindView
     * @receiver T
     * @param c Class<D>
     * @param cRun Function2<D, Int, Unit>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>, cRun: (D, Int) -> Unit, run: (dataBinding: D, item: I, postion: Int) -> Unit, isSetBean: Boolean? = true) = TypeSupportAdaper.build(realData(), c, cRun, run, isSetBean)

    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(c: Class<D>, cRun: (D, Int) -> Unit, run: (dataBinding: D, item: I, postion: Int) -> Unit) = TypeSupportAdaper.build(realData(), c, cRun, run, true)

    /**
     * 返回一个adapter ，有一个onCreatView，有一个onBindView
     * @receiver T
     * @param type Int
     * @param c Class<D>
     * @param cRun Function2<D, Int, Unit>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(type: Int, c: Class<D>, cRun: (D, Int) -> Unit, run: (dataBinding: D, item: I, postion: Int) -> Unit) = TypeSupportAdaper.build(type, realData(), c, cRun, run)

    /**
     * 返回一个adapter ，有一个onCreatView，有一个onBindView
     * @receiver T
     * @param type Int
     * @param c Class<D>
     * @param cRun Function2<D, Int, Unit>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(type: Int, c: Class<D>, run: (dataBinding: D, item: I, postion: Int) -> Unit) = TypeSupportAdaper.build(type, realData(), c, { d, t -> }, run)


    /**
     * 返回一个adapter ，有一个onCreatView，有一个onBindView
     * @receiver T
     * @param type Int
     * @param c Class<D>
     * @param cRun Function2<D, Int, Unit>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, I> T.adapter(typeBreakMethod: (Int) -> Int) = TypeSupportAdaper.build(realData(), typeBreakMethod)

//    @JvmStatic
//    fun pickNewPhoto(headImagePath: String?, headImageRequest: Int) {
//        val image = ArrayList<LocalMedia>()
//        headImagePath?.let {
//            val l = LocalMedia();
//            l.path = it;
//            image.add(l)
//        }
//        PersonalInformationActivity.choosePicture(image, headImageRequest)
//    }

    @JvmStatic
    fun <I, V : ViewDataBinding> IRecycleViewClient<I>.buildInitRecycleView(c: Class<V>, r: (V, I, Int) -> Unit) {
        buildInitRecycleView(true, true, LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false), getSource()?.adapter(c, r))
    }

    @JvmStatic
    fun <I> IRecycleViewClient<I>.buildInitRecycleView(r: RecyclerView.Adapter<*>) {
        buildInitRecycleView(true, true, LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false), r)
    }


    @JvmStatic
    fun IRecycleViewClient<*>.buildInitRecycleView(isRefresh: Boolean, isMoreLoad: Boolean, adaper: RecyclerView.Adapter<*>?) {
        buildInitRecycleView(isRefresh, isMoreLoad, LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false), adaper)
    }

    @JvmStatic
    fun IRecycleViewClient<*>.buildInitRecycleView(isRefresh: Boolean, isMoreLoad: Boolean, layoutManager: RecyclerView.LayoutManager, adaper: RecyclerView.Adapter<*>?) {
        recycleView()?.let {
            it.layoutManager = layoutManager
            it.adapter = adaper
        }
        refreshView()?.let {
            it.refreshLayoutInit(getPageEngine(), isRefresh, isMoreLoad)
        }
    }

}