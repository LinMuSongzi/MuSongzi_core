package com.musongzi.comment

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.musongzi.comment.business.DoubleLimiteBusiness
import com.musongzi.comment.util.ApkUtil
import com.musongzi.core.StringChooseBean
import com.musongzi.core.annotation.CollecttionsEngine
import com.musongzi.comment.activity.NormalFragmentActivity
import com.musongzi.core.base.bean.BusinessInfo
import com.musongzi.core.base.bean.FragmentDescribe
import com.musongzi.core.base.bean.StyleMessageDescribe
import com.musongzi.comment.business.SupproActivityBusiness
import com.musongzi.core.base.business.collection.BaseMoreViewEngine
import com.musongzi.core.base.business.collection.ViewListPageFactory
import com.musongzi.core.base.fragment.CollectionsViewFragment
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager
import com.musongzi.core.base.vm.CollectionsViewModel
import com.musongzi.core.itf.IHolderSavedStateHandle
import com.musongzi.core.itf.ILifeSaveStateHandle
import com.musongzi.core.itf.INeed
import com.musongzi.core.itf.page.IPageEngine
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.ActivityThreadHelp.getCurrentApplication
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.util.ScreenUtil
import io.reactivex.rxjava3.core.Observable

object ExtensionMethod {


    @JvmStatic
    fun CollectionsViewFragment.asInterfaceByEngine(runOnResume: (page: IPageEngine<*>?) -> Unit) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            runOnResume.invoke(this@asInterfaceByEngine.getPageEngine())
        } else {
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
    fun <E : BaseMoreViewEngine<*, *>> Class<E>.convertFragemnt(
        data: Bundle? = null,
        onInfoObserver: ((info: CollectionsViewModel.CollectionsInfo) -> Unit)? = null
    ): Fragment {
        val cAnnotation: CollecttionsEngine? = InjectionHelp.findAnnotation(this)
        val mCollectionsInfo = cAnnotation?.let {
            CollectionsViewModel.CollectionsInfo(it)
        } ?: CollectionsViewModel.CollectionsInfo()
        onInfoObserver?.invoke(mCollectionsInfo)
        val bundle = Bundle();
        data?.let {
            bundle.putBundle(CollecttionsEngine.B, it)
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
        val info: CollectionsViewModel.CollectionsInfo? =
            arguments?.getParcelable(ViewListPageFactory.INFO_KEY);
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

    @JvmStatic
    @JvmOverloads
    fun <F : Fragment, A : NormalFragmentActivity> Class<F>.startActivityNormal(
        activity: Class<A>? = null,
        mStyleMessageDescribe: StyleMessageDescribe,
        dataBundle: Bundle? = null,
        businessClassName: String? = null
    ) {
        ActivityLifeManager.getInstance().getTopActivity()?.let {
            val intent = Intent(it, activity ?: NormalFragmentActivity::class.java)
            val fInfo = FragmentDescribe(
                this.name,
                mStyleMessageDescribe,
                if (businessClassName != null) BusinessInfo(businessClassName) else null
            )
            intent.putExtra(SupproActivityBusiness.ACTIVITY_DESCRIBE_INFO_KEY, fInfo)
            dataBundle?.let { b ->
                intent.putExtra(SupproActivityBusiness.BUNDLE_KEY, b)
            }
            it.startActivity(intent)
        }
    }


    @JvmStatic
    @JvmOverloads
    fun <F : Fragment, A : NormalFragmentActivity> Class<F>.startActivityNormal(
        activity: Class<A>? = null,
        title: String? = null,
        barColor: Int = R.color.bg_white,
        dataBundle: Bundle? = null,
        businessClassName: String? = null
    ) {
        (ActivityLifeManager.getInstance().getTopActivity() ?: getCurrentApplication()).let {
            val intent = Intent(it, activity ?: NormalFragmentActivity::class.java)
            val fInfo = FragmentDescribe(
                this.name,
                StyleMessageDescribe(title, barColor),
                if (businessClassName != null) BusinessInfo(businessClassName) else null
            )
            intent.putExtra(SupproActivityBusiness.ACTIVITY_DESCRIBE_INFO_KEY, fInfo)
            dataBundle?.let { b ->
                intent.putExtra(SupproActivityBusiness.BUNDLE_KEY, b)
            }
            if (it is Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
     * 获取基于“key”的可观察的value
     */
    @JvmStatic
    fun <T> String.getSaveStateValue(holder: IHolderSavedStateHandle): T? {
        return holder.getHolderSavedStateHandle()[this]
    }

    @JvmStatic
    fun <T> String.savedStateAllLiveChangevalue(values: T) {
        val r: (IHolderSavedStateHandle?, Activity) -> Unit = { f, activity ->
            if (!activity.isFinishing) {
                f?.getHolderSavedStateHandle()?.set(this, values)
            }
        }
        for (activity in ActivityLifeManager.getInstance().getLifeActivityList()) {
            if (activity is FragmentActivity) {
                for (f in activity.supportFragmentManager.fragments) {
                    r.invoke(f as? IHolderSavedStateHandle, activity)
                }
            }
        }
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
        return ApkUtil.getResUriString(this)
    }

    /**
     * viewpage 绑定一个adapter
     * @receiver Page
     * @param f FragmentManager
     * @param fragmentList List<Fragment>
     * @return Page
     */
    @JvmStatic
    fun <Page : ViewPager2> Page.bindAdapter(
        f: Fragment,
        fragmentList: List<Fragment>,
        offscreen: Int? = null
    ): Page {
        offscreenPageLimit = offscreen ?: fragmentList.size
        adapter = object : FragmentStateAdapter(f) {
            override fun getItemCount() = fragmentList.size

            override fun createFragment(position: Int) = fragmentList[position]

        }
        return this
    }

    /**
     * viewpage 绑定一个adapter
     * @receiver Page
     * @param f FragmentManager
     * @param fragmentList List<Fragment>
     * @return Page
     */
    @JvmStatic
    fun <Page : ViewPager2> Page.bindAdapter(
        fragmentManager: FragmentManager,
        lifecycle: LifecycleOwner,
        fragmentList: List<Fragment>,
        offscreen: Int? = null
    ): Page {
        offscreenPageLimit = offscreen ?: fragmentList.size
        adapter = object : FragmentStateAdapter(fragmentManager, lifecycle.lifecycle) {
            override fun getItemCount() = fragmentList.size

            override fun createFragment(position: Int) = fragmentList[position]

        }
        return this
    }


}