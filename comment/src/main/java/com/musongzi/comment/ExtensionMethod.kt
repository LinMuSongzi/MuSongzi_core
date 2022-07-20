package com.musongzi.comment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.musongzi.comment.ExtensionMethod.getResUri
import com.musongzi.comment.business.DoubleLimiteBusiness
import com.musongzi.comment.util.ApkUtil
import com.musongzi.core.MszObserver
import com.musongzi.core.StringChooseBean
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.core.base.activity.NormalFragmentActivity
import com.musongzi.core.base.adapter.TypeSupportAdaper
import com.musongzi.core.base.bean.FragmentEventInfo
import com.musongzi.core.base.bean.StyleMessageInfo
import com.musongzi.core.base.business.HandlerChooseBusiness
import com.musongzi.core.base.business.SupproActivityBusiness
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.ICollectionsViewEngine
import com.musongzi.core.base.business.collection.ViewListPageFactory
import com.musongzi.core.base.client.IRecycleViewClient
import com.musongzi.core.base.fragment.CollectionsViewFragment
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.base.vm.IHandlerChooseViewModel
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.INeed
import com.musongzi.core.itf.holder.IHolderViewModelProvider
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.itf.page.ISource
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.ActivityThreadHelp.getCurrentApplication
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.util.ScreenUtil
import com.musongzi.core.util.TextUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import kotlin.jvm.Throws

object ExtensionMethod {

    @JvmStatic
    fun <T> T.layoutInflater(p: ViewGroup, res: Int) =
        LayoutInflater.from(getCurrentApplication()).inflate(res, p, false);

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
            javaClass.getMethod("set${TextUtil.capitalizationText(entity)}", clazz)
                .invoke(this, entityObject)
            Log.i("businessSet", ": succeed " + entity.javaClass.simpleName)
        }
    }

    @JvmStatic
    fun SmartRefreshLayout.refreshLayoutInit(
        p: IPageEngine<*>?,
        isEnableRefresh: Boolean,
        isEnableLoadMore: Boolean
    ) {
        refreshLayoutInit(this, p, isEnableRefresh, isEnableLoadMore, 500)
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

    fun <T : ViewDataBinding, R> R.dataBindingInflate(clazz: Class<T>, view: ViewGroup): T? {
        return exceptionRunByReturn {
            val method = clazz.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
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

    fun <T> Observable<T>.sub(c: Consumer<T>) {
        subscribe(MszObserver(c))
    }

    fun Any.toJson(): String {
        return Gson().toJson(this)
    }

    fun <T> ICollectionsViewEngine<*>.getApi(c: Class<T>): T {
        return RetrofitManager.getInstance().getApi(c, getRefreshViewModel())
    }

    @JvmStatic
    fun CollectionsViewFragment.asInterfaceByEngine(runOnResume: (page: IPageEngine<*>?) -> Unit) {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
            runOnResume.invoke(this@asInterfaceByEngine.getPageEngine())
        }else {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    runOnResume.invoke(this@asInterfaceByEngine.getPageEngine())
                    owner.lifecycle.removeObserver(this)
                }
            })
        }
    }


    @JvmStatic
    @JvmOverloads
    fun <E : BaseMoreViewEngine<*, *>> Class<E>.convertFragemnt(data: Bundle? = null, onInfoObserver: ((info: CollectionsViewModel.CollectionsInfo) -> Unit)? = null): Fragment {
        val cAnnotation: CollecttionsEngine? = InjectionHelp.findAnnotation(this)
        val mCollectionsInfo = cAnnotation?.let {
            CollectionsViewModel.CollectionsInfo(it)
        } ?: CollectionsViewModel.CollectionsInfo()
        onInfoObserver?.invoke(mCollectionsInfo)
        val bundle = Bundle();
        data?.let {
            bundle.putBundle(CollecttionsEngine.B,it)
        }
        ModelFragment.composeProvider(bundle, false)
        mCollectionsInfo.engineName = name
        bundle.putParcelable(ViewListPageFactory.INFO_KEY, mCollectionsInfo)
        val collectionsFragment = CollectionsViewFragment();
        collectionsFragment.arguments = bundle
        return collectionsFragment
    }

    @JvmStatic
    fun CollectionsViewFragment.updateCollectionFragmentInfo(update: (CollectionsViewModel.CollectionsInfo) -> Unit): Fragment {
        val info: CollectionsViewModel.CollectionsInfo? = arguments?.getParcelable(ViewListPageFactory.INFO_KEY);
        info?.let {
            update.invoke(it)
            arguments?.putParcelable(ViewListPageFactory.INFO_KEY, it)
        }
        return this;
    }

    @JvmStatic
    fun CollectionsViewFragment.bindTotalSize(l: LifecycleOwner, run: Observer<Int>) {
        totalLiveData.observe(l, run);
    }

    fun String.bean() = StringChooseBean().let {
        it.title = this
        it
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

    @Throws(Exception::class)
    fun isDebug(): Boolean {
        val context = getCurrentApplication();
        val bc = "${context.packageName}$.BuildConfig"
        Log.i("isDebug", "isDebug: $bc")
        val bcIntsance = ExtensionMethod::class.java.classLoader!!.loadClass(bc)

        return bcIntsance.let {
            val f = it.getDeclaredField("BUILD_TYPE");
            val type = f.get(null);
            "debug" == type
        }

    }

    @JvmStatic
    @JvmOverloads
    fun <F : Fragment> Class<F>.startActivityNormal(
        title: String? = null,
        barColor: Int = Color.WHITE,
        dataBundle: Bundle? = null
    ) {
        ActivityLifeManager.getInstance().getTopActivity()?.let {
            val intent = Intent(it, NormalFragmentActivity::class.java)
            val fInfo = FragmentEventInfo(this.name, StyleMessageInfo(title, barColor))
            intent.putExtra(SupproActivityBusiness.INFO_KEY, fInfo)
            dataBundle?.let { b ->
                intent.putExtra(SupproActivityBusiness.BUNDLE_KEY, b)
            }
            it.startActivity(intent)
        }
    }

    fun <A : Activity> Class<A>.startActivity() {
        ActivityLifeManager.getInstance().getTopActivity()?.let {
            try {
                it.startActivity(Intent(it, this))
            } catch (e: Exception) {
                toast("无法打开${this.canonicalName}活动~", it)
                e.printStackTrace()
            }
        }

    }

    fun toast(msg: String?, activity: Activity? = null) {
        msg?.let {
            if (Thread.currentThread() != Looper.getMainLooper().thread) {
                Handler(Looper.getMainLooper()).post {
                    toast(it)
                }
                return
            }
            val c = activity ?: ActivityLifeManager.getInstance().getTopActivity()
            if (c != null && !c.isFinishing) {
                Toast.makeText(c, it, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getCurrentApplication(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 保存基于“key”的value 存储于bundle基于SavedStateHandler api
     */
    @JvmStatic
    fun <T> String.saveStateChange(holder: IHolderSavedStateHandle, v: T) {
        holder.getHolderSavedStateHandle()[this] = v
    }


    /**
     * 观察数据基于“key”的livedate，
     * isRemove 是否此次监听仅为一次
     */
    @JvmOverloads
    @JvmStatic
    fun <T> String.liveSaveStateObserver(
        holder: ILifeSaveStateHandle,
        isRemove: Boolean = false,
        observer: Observer<T>
    ) {
        holder.getThisLifecycle()?.let {
            val liveData = holder.getHolderSavedStateHandle().getLiveData<T>(this);
            if (isRemove) {
                liveData.observe(it, object : Observer<T> {
                    override fun onChanged(t: T) {
                        observer.onChanged(t)
                        liveData.removeObserver(this)
                    }
                })
            } else {
                liveData.observe(it, observer)
            }
        }
    }

    /**
     * 获取基于“key”的可观察的livedata
     */
    @JvmStatic
    fun <T> String.getSaveStateLiveData(holder: IHolderSavedStateHandle): LiveData<T> {
        return holder.getHolderSavedStateHandle().getLiveData(this);
    }

    /**
     * 观察数据基于“key”的livedate，
     * 观察者返回值 ： true 表示此次观察将会移除观察者。
     *            ： false 表示此次观察不会移除观察者
     */
    @JvmStatic
    fun <T> String.liveSaveStateObserverCall(
        holder: ILifeSaveStateHandle,
        observer: (call: T) -> Boolean
    ) {
        holder.getThisLifecycle()?.let {
            val liveData = holder.getHolderSavedStateHandle().getLiveData<T>(this);
            liveData.observe(it, object : Observer<T> {
                override fun onChanged(t: T) {
                    if (observer.invoke(t)) {
                        liveData.removeObserver(this)
                    }
                }
            })
        }
    }


    @JvmStatic
    fun INeed.doubleLimiter(k: String, run: Runnable) {
        getNext(DoubleLimiteBusiness::class.java)?.check(k, run)
    }

    @JvmStatic
    fun INeed.doubleLimiter(k: String, limiterTime: Int, run: Runnable) {
        getNext(DoubleLimiteBusiness::class.java)?.apply {
            limiter = limiterTime
            check(k, run)
        }
    }

    @JvmStatic
    fun EditText.search(call: (String) -> Unit) {
        setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) {
                        call(text.toString())
                        return true
                    }
                }
                return false
            }
        })
    }

    @JvmStatic
    fun EditText.searchByObservable(call: (Observable<String>) -> Unit) {
        setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) {
                        call(Observable.create { it.onNext(text.toString()) })
                        return true
                    }
                }
                return false
            }
        })
    }

    @JvmStatic
    fun Int.dp(): Float {
        return ScreenUtil.dp2px(this.toFloat()).toFloat()
    }

    @JvmStatic
    fun Int.getResUri(): Uri {
        return ApkUtil.getResUri(this)
    }

    @JvmStatic
    fun Int.getDrawable(): Drawable {
        return ActivityCompat.getDrawable(ActivityThreadHelp.getCurrentApplication(), this)!!
    }

    @JvmStatic
    fun Int.getResUriString(): String {
        return ApkUtil.getResUri(this).toString()
    }

//    @JvmStatic
//    fun String?.showWeb() {
//        this?.apply {
//            ApkUtil.startSimpleWebHtml(this, false)
//        }
//    }
//
//    @JvmStatic
//    fun String?.showHtml() {
//        this?.apply {
//            ApkUtil.startSimpleWebHtml(this, true)
//        }
//    }

}