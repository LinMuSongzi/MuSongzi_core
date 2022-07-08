package com.musongzi.core.base.business

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.musongzi.core.R
import com.musongzi.core.base.bean.FragmentEventInfo
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.INotifyDataSetChanged
import com.musongzi.core.itf.IWant
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.util.InjectionHelp.injectBusiness
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.RxLifecycle
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.android.RxLifecycleAndroid
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/*** created by linhui * on 2022/7/6 */
class SupproActivityBusiness : BaseMapBusiness<IHolderLifecycle>(), ISupprotActivityBusiness {


    override fun checkEvent() {
        val h = iAgent as HolderLifecycleImpl;
        h.activity.getHolderContext()?.let {
            if (it is Activity) {
                it.setContentView(R.layout.activity_normal_fragment)
                val fragmentEventInfo: FragmentEventInfo? = h.getArguments()?.getParcelable(INFO_KEY)
                if (fragmentEventInfo != null) {
                    val titleView: TextView = it.findViewById(R.id.tv_title)
                    titleView.text = fragmentEventInfo.sinfo.title
                    if (it is AppCompatActivity) {
                        val dataBundle = h.getArguments()?.getBundle(BUNDLE_KEY)
                        it.supportFragmentManager.beginTransaction().replace(
                            R.id.id_content_layout,
                            it.classLoader.loadClass(fragmentEventInfo.className) as Class<out Fragment>,
                            dataBundle,
                            it.javaClass.name+"_fragment"
                        ).commitNow()
                    }
                }
            }
        }
    }


    override fun getHolderContext(): Context? {
        return (iAgent as IHolderActivity).getHolderContext()
    }

    class HolderLifecycleImpl(private val savedInstance: Bundle?, var activity: IHolderContext) :
        IHolderActivity, IHolderLifecycle, DefaultLifecycleObserver {

        init {
            (activity as LifecycleOwner).lifecycle.addObserver(this)
        }

        private val activityViewModelProvider: ViewModelProvider by lazy {
            ViewModelProvider(
                activity as ViewModelStoreOwner, SupportViewModelFactory(
                    this,
                    activity as SavedStateRegistryOwner,
                    savedInstance
                )
            )
        }


        override fun getMainLifecycle(): IHolderLifecycle? {
            return ((activity as? Fragment)?.requireActivity() as? IHolderLifecycle) ?: this
        }

        override fun getThisLifecycle(): LifecycleOwner? {
            return activity as? LifecycleOwner
        }

        override fun getHolderActivity(): FragmentActivity? =
            activity.getHolderContext() as? FragmentActivity

        override fun getClient(): IClient {
            return (activity as? IClient) ?: this
        }

        override fun getHolderContext(): Context? {
            return activity.getHolderContext()
        }

        override fun putArguments(d: Bundle?) {
            (activity as? IHolderArguments<Bundle>)?.putArguments(d)
        }

        override fun getArguments(): Bundle? =
            (activity.getHolderContext() as? Activity)?.intent?.extras

        override fun handlerArguments() {
            (activity as? IHolderArguments<Bundle>)?.handlerArguments()
        }

        override fun notifyDataSetChanged() {
            (activity as? INotifyDataSetChanged)?.notifyDataSetChanged()
        }

        override fun notifyDataSetChangedItem(postiont: Int) {
            (activity as? INotifyDataSetChanged)?.notifyDataSetChangedItem(postiont)
        }

        override fun showDialog(msg: String?) {
            (activity as? INotifyDataSetChanged)?.showDialog(msg)
        }

        override fun disimissDialog() {
            (activity as? INotifyDataSetChanged)?.disimissDialog()
        }

        override fun disconnect() {
            (activity as? INotifyDataSetChanged)?.disconnect()
        }

        override fun topViewModelProvider(): ViewModelProvider {
            return (activity as? IHolderViewModelProvider)?.topViewModelProvider()
                ?: activityViewModelProvider
        }

        override fun thisViewModelProvider(): ViewModelProvider {
            return topViewModelProvider()
        }

        override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
            return (activity as? IWant)?.bindToLifecycle() ?: RxLifecycleAndroid.bindActivity(
                lifecycleSubject
            )
        }

        private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

        @CheckResult
        fun lifecycle(): Observable<ActivityEvent> {
            return lifecycleSubject.hide()
        }

        @CheckResult
        fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
            return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
        }

        @CallSuper
        protected fun onCreate(savedInstanceState: Bundle?) {
            lifecycleSubject.onNext(ActivityEvent.CREATE)
        }

        @CallSuper
        protected fun onStart() {
            lifecycleSubject.onNext(ActivityEvent.START)
        }

        @CallSuper
        protected fun onResume() {
            lifecycleSubject.onNext(ActivityEvent.RESUME)
        }

        @CallSuper
        protected fun onPause() {
            lifecycleSubject.onNext(ActivityEvent.PAUSE)
        }

        @CallSuper
        protected fun onStop() {
            lifecycleSubject.onNext(ActivityEvent.STOP)
        }

        @CallSuper
        protected fun onDestroy() {
            lifecycleSubject.onNext(ActivityEvent.DESTROY)
        }

    }


    class SupportViewModelFactory(
        val activity: IHolderActivity,
        owner: SavedStateRegistryOwner,
        private val defaultArgs: Bundle?
    ) :
        AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            val t = modelClass.newInstance()
            (t as? IHolderViewModel<*, *>)?.let {
                if (it.getHolderSavedStateHandle() == null) {
                    it.setHolderSavedStateHandle(handle)
                }
                InjectionHelp.injectViewModel(activity, defaultArgs, t);
            }
            return t
        }

    }


    companion object {

        const val BUNDLE_KEY = "support_bundle_key"
        const val INFO_KEY = "support_activity_key"

        fun create(bundle: Bundle?, activity: IHolderContext): ISupprotActivityBusiness {
            val impl = HolderLifecycleImpl(bundle, activity)
            val business: SupproActivityBusiness = injectBusiness(
                SupproActivityBusiness::class.java,
                impl
            )!!
//            if (business.holderLifecycleImpl == impl) {
                Log.i(business.TAG, "create: 初始化成功 $impl")
//            }
            return business
        }

    }

}