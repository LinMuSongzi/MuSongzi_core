package com.musongzi.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.musongzi.core.base.adapter.TypeSupportAdaper
import com.musongzi.core.base.business.HandlerChooseBusiness
import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.IBusiness
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.IHolderNeed
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.TextUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.CompletableOnSubscribe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import java.text.SimpleDateFormat
import java.util.*

/*** created by linhui * on 2022/7/20 */
object ExtensionCoreMethod {

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun Long.convertCommentTime(): String {
        if (this == 0L) {
            return ""
        }
        val mCld = Calendar.getInstance();
        val thisYear = mCld.get(Calendar.YEAR);
        mCld.time = Date(this)
        return if (mCld.get(Calendar.YEAR) != thisYear) {
            SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(this);
        } else {
            SimpleDateFormat("MM月dd日 HH:mm").format(this);
        }
    }


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

    fun <D : ViewDataBinding, R> R.dataBindingInflate(clazz: Class<D>, view: ViewGroup): D? {
        return exceptionRunByReturn {
            val method = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            method.invoke(
                null,
                LayoutInflater.from(ActivityThreadHelp.getCurrentApplication()),
                view,
                false
            ) as? D
        }
    }

    fun ViewDataBinding.businessSet(business: Any) {
        exceptionRun {
            javaClass.getMethod("setBusiness", business::class.java).invoke(this, business)
            Log.i("businessSet", ": succeed " + business.javaClass.simpleName)
        }
    }

    fun <T> ViewDataBinding.entitySet(entity: String, clazz: Class<T>, entityObject: T?) {
        exceptionRun {
            javaClass.getMethod("set${TextUtil.capitalizationText(entity)}", clazz)
                .invoke(this, entityObject)
            Log.i("businessSet", ": succeed " + entity.javaClass.simpleName)
        }
    }


    fun <B : IBusiness> Class<B>.getNeedNext(holder: IHolderNeed?): B? {
        return holder?.getHolderNeed()?.getNext(this)
    }

    /**
     * 注意如果当前的IHandlerChooseViewModel 子类不是
     */
    fun <I : IHandlerChooseViewModel<*>> I.wantPick() = getHolderBusiness()?.let {
        return@let it.getNext(HandlerChooseBusiness::class.java)
    } ?: HandlerChooseBusiness(this)

    fun <V : ViewModel> Class<V>.topInstance(b: IHolderViewModelProvider?): V? {
        return b?.topViewModelProvider()?.get(this)
    }

    fun <V : ViewModel> Class<V>.thisInstance(b: IHolderViewModelProvider?): V? {
        return b?.thisViewModelProvider()?.get(this)
    }

    fun String.bean() = StringChooseBean().let {
        it.title = this
        it
    }

    fun <T> T.threadStart(r: Runnable) {
        Thread(r).start()
//        ThreadUtil.startThread(r)
    }

    fun <T : Any> Observable<T>.sub(c: Consumer<T>) {
        subscribe(BaseObserver(c))
    }

    fun <T : Any> Observable<T>.sub(runOnDisposable: Disposable.() -> Unit, sub: Consumer<T>) {
        subscribe(BaseObserver(sub).apply {
            this.runOnDisposable = runOnDisposable
        })
    }

    fun Any.toJson(): String {
        return Gson().toJson(this)
    }

    @JvmStatic
    fun Int.layoutInflater(p: ViewGroup?): View =
        LayoutInflater.from(ActivityThreadHelp.getCurrentApplication()).inflate(this, p, false);

    @JvmStatic
    fun SmartRefreshLayout?.refreshLayoutInit(
        p: IPageEngine<*>?,
        isEnableRefresh: Boolean,
        isEnableLoadMore: Boolean
    ) {
        if (this != null) {
            refreshLayoutInit(this, p, isEnableRefresh, isEnableLoadMore, 500)
        }
    }

    @JvmStatic
    fun SmartRefreshLayout.refreshLayoutInit(p: IPageEngine<*>?) {
        refreshLayoutInit(this, p, true, true, 500)
    }

    @JvmStatic
    fun refreshLayoutInit(
        refreshLayout: SmartRefreshLayout,
        p: IPageEngine<*>?,
        isEnableRefresh: Boolean,
        isEnableLoadMore: Boolean,
        time: Int
    ) {
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

//    @Throws(Exception::class)
//    fun isDebug(): Boolean {
//        val context = ActivityThreadHelp.getCurrentApplication();
//        val bc = "${context.packageName}$.BuildConfig"
//        Log.i("isDebug", "isDebug: $bc")
//        val bcIntsance = ExtensionMethod::class.java.classLoader!!.loadClass(bc)
//
//        return bcIntsance.let {
//            val f = it.getDeclaredField("BUILD_TYPE");
//            val type = f.get(null);
//            "debug" == type
//        }
//
//    }


    @JvmStatic
    fun <I, T : ISource<I>> T.adapter() = TypeSupportAdaper(this.realData())

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
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        c: Class<D>,
        run: ((dataBinding: D, item: I, postion: Int) -> Unit)?
    ) = TypeSupportAdaper.build(realData(), c, run
        ?: { _, _, _ -> })

    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        c: Class<D>,
        run: ((dataBinding: D, item: I, postion: Int) -> Unit)?,
        isSetBean: Boolean?
    ) = TypeSupportAdaper.build(
        realData(), c, run
            ?: { _, _, _ -> }, isSetBean
    )

    /**
     * 返回一个adapter ，有一个onCreatView，有一个onBindView
     * @receiver T
     * @param c Class<D>
     * @param cRun Function2<D, Int, Unit>
     * @param run Function3<[@kotlin.ParameterName] D, [@kotlin.ParameterName] I, [@kotlin.ParameterName] Int, Unit>
     * @return TypeSupportAdaper<[@kotlin.ParameterName] I>
     */
    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        c: Class<D>,
        cRun: (D, Int) -> Unit,
        run: (dataBinding: D, item: I, postion: Int) -> Unit,
        isSetBean: Boolean? = true
    ) = TypeSupportAdaper.build(realData(), c, cRun, run, isSetBean)

    @JvmStatic
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        c: Class<D>,
        cRun: (D, Int) -> Unit,
        run: (dataBinding: D, item: I, postion: Int) -> Unit
    ) = TypeSupportAdaper.build(realData(), c, cRun, run, true)

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
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        type: Int,
        c: Class<D>,
        cRun: (D, Int) -> Unit,
        run: (dataBinding: D, item: I, postion: Int) -> Unit
    ) = TypeSupportAdaper.build(type, realData(), c, cRun, run)

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
    fun <T : ISource<I>, D : ViewDataBinding, I> T.adapter(
        type: Int,
        c: Class<D>,
        run: (dataBinding: D, item: I, postion: Int) -> Unit
    ) = TypeSupportAdaper.build(type, realData(), c, { d, t -> }, run)


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
    fun <T : ISource<I>, I> T.adapter(typeBreakMethod: (Int) -> Int) =
        TypeSupportAdaper.build(realData(), typeBreakMethod)


    @JvmStatic
    fun RecyclerView.linearLayoutManager(adapterMethod: (LinearLayoutManager) -> RecyclerView.Adapter<*>) {
        linearLayoutManager(LinearLayoutManager.VERTICAL, adapterMethod);
    }

    @JvmStatic
    fun RecyclerView.linearLayoutManager(
        or: Int,
        adapterMethod: (LinearLayoutManager) -> RecyclerView.Adapter<*>
    ) {
        val mLayoutManager = LinearLayoutManager(null, or, false)
        val a = adapterMethod(mLayoutManager)
        layoutManager = mLayoutManager
        adapter = a
    }

    @JvmStatic
    fun RecyclerView.gridLayoutManager(
        span: Int,
        adapterMethod: (LinearLayoutManager) -> RecyclerView.Adapter<*>
    ) {
        gridLayoutManager(span, GridLayoutManager.VERTICAL, adapterMethod)
    }

    @JvmStatic
    fun RecyclerView.gridLayoutManager(
        span: Int,
        or: Int,
        adapterMethod: (LinearLayoutManager) -> RecyclerView.Adapter<*>
    ) {
        val mLayoutManager = GridLayoutManager(null, span, or, false)
        val a = adapterMethod(mLayoutManager)
        layoutManager = mLayoutManager
        adapter = a
    }

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
    fun <I, V : ViewDataBinding> IRecycleViewClient<I>.buildInitRecycleView(
        c: Class<V>,
        r: (V, I, Int) -> Unit
    ) {
        buildInitRecycleView(
            true,
            true,
            LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false),
            getSource()?.adapter(c, r)
        )
    }

    @JvmStatic
    fun <I> IRecycleViewClient<I>.buildInitRecycleView(r: RecyclerView.Adapter<*>) {
        buildInitRecycleView(
            true,
            true,
            LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false),
            r
        )
    }


    @JvmStatic
    fun IRecycleViewClient<*>.buildInitRecycleView(
        isRefresh: Boolean,
        isMoreLoad: Boolean,
        adaper: RecyclerView.Adapter<*>?
    ) {
        buildInitRecycleView(
            isRefresh,
            isMoreLoad,
            LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false),
            adaper
        )
    }

    @JvmStatic
    fun Int.androidColorGet() =
        ActivityCompat.getColor(ActivityThreadHelp.getCurrentApplication(), this)

    @JvmStatic
    fun IRecycleViewClient<*>.buildInitRecycleView(
        isRefresh: Boolean,
        isMoreLoad: Boolean,
        layoutManager: RecyclerView.LayoutManager,
        adaper: RecyclerView.Adapter<*>?
    ) {
        recycleView()?.let {
            it.layoutManager = layoutManager
            it.adapter = adaper
        }
        refreshView()?.let {
            it.refreshLayoutInit(getPageEngine(), isRefresh, isMoreLoad)
        }
    }

    @JvmStatic
    fun <T> Class<T>.getApi(want: IWant? = null): T? {
        if (!this.isInterface) {
            return null
        }
        return RetrofitManager.getInstance().getApi(this, want)
    }


    @JvmStatic
    fun <F : Fragment> Class<F>.instance(bundle: Bundle? = null): F {
        return bundle?.let {
            newInstance().let { f ->
                f.arguments = bundle
                f
            }
        } ?: newInstance()
    }

    fun <V : ViewModel> Class<V>.getViewModel(vp: ViewModelProvider): V {
        return vp.get(this)
    }

}