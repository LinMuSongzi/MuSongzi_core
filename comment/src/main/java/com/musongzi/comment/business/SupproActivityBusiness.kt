package com.musongzi.comment.business

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.gyf.immersionbar.ImmersionBar
import com.musongzi.core.R
import com.musongzi.core.base.bean.ActivityDescribe
import com.musongzi.core.base.bean.FragmentDescribe
import com.musongzi.core.base.bean.StyleMessageDescribe
import com.musongzi.core.base.business.BaseMapBusiness
import com.musongzi.core.base.business.itf.ISupprotActivityBusiness
import com.musongzi.core.base.map.LocalSavedHandler
import com.musongzi.core.databinding.ActivityNormalFragmentBinding
import com.musongzi.core.itf.*
import com.musongzi.core.itf.holder.*
import com.musongzi.core.util.InjectionHelp
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.RxLifecycle
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.android.RxLifecycleAndroid
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/*** created by linhui * on 2022/7/6
 *
 * 一个辅助activity嵌入fragment的辅助类
 *
 *
 * */
class SupproActivityBusiness : BaseMapBusiness<IHolderLifecycle>(), ISupprotActivityBusiness {

    private val mLocalSavedHandler: ISaveStateHandle by lazy {
        LocalSavedHandler()
    }
    lateinit var dataBinding: ActivityNormalFragmentBinding

    override fun checkEvent() {
        val h = iAgent as HolderLifecycleImpl;
        h.activity.getHolderContext()?.let { context ->
            if (context is Activity) {
                dataBinding = DataBindingUtil.setContentView(context, R.layout.activity_normal_fragment)
                val activityDescribe: ActivityDescribe? = h.getArguments()?.getParcelable(ACTIVITY_DESCRIBE_INFO_KEY)
                activityDescribe?.let { a ->
                    handlerWindowFlag(a)
                    (a.parcelable as? FragmentDescribe)?.let { fragmentDescribe ->
                        handlerBarBusiness(context, fragmentDescribe.sinfo!!)
                        if (context is AppCompatActivity) {
                            var dataBundle: Bundle? = h.getArguments()?.getBundle(BUNDLE_KEY)
                            dataBundle = fragmentDescribe.businessInfo?.let {
                                handlerBusinesInstanceInfo(fragmentDescribe, dataBundle)
                            } ?: dataBundle
                            val fragment: Fragment = InjectionHelp.injectFragment(
                                context.classLoader,
                                fragmentDescribe.className,
                                dataBundle
                            )
                            context.supportFragmentManager.beginTransaction().replace(
                                R.id.id_content_layout,
                                fragment,
                                fragmentDescribe.tag
                            ).commitNow()
                        }
                        true
                    }
                }
            }
        }
    }

    override fun getLocalHolderSavedStateHandle(): ISaveStateHandle {
      return  mLocalSavedHandler
    }

    private fun handlerWindowFlag(a: ActivityDescribe) {

    }

    /**
     * 处理business 实例问题
     */
    private fun handlerBusinesInstanceInfo(
        fragmentDescribe: FragmentDescribe,
        dataBundle: Bundle?
    ): Bundle =
        (dataBundle ?: Bundle()).apply {
            putParcelable(
                InjectionHelp.BUSINESS_NAME_KEY,
                fragmentDescribe.businessInfo
            )
        }

    /**
     * 处理状态栏
     */
    private fun handlerBarBusiness(it: Activity, describe: StyleMessageDescribe) {
        ImmersionBar.with(it).apply {
            statusBarDarkFont(describe.statusTextColorFlag == 1)
        }.statusBarColor(describe.statusColor).fitsSystemWindows(true).init()
        dataBinding.titleLayout.bean = describe
    }


    override fun getHolderContext(): Context? {
        return (iAgent as IHolderActivity).getHolderContext()
    }

    override fun topViewModelProvider(): ViewModelProvider? {
       return (iAgent as? IHolderViewModelProvider)?.topViewModelProvider()
    }

    override fun thisViewModelProvider(): ViewModelProvider? {
        return (iAgent as? IHolderViewModelProvider)?.thisViewModelProvider()
    }

    override fun getHolderDataBinding(): ViewDataBinding {
        return dataBinding
    }

    internal class HolderLifecycleImpl(private val savedInstance: Bundle?, var activity: IHolderContext) :
        IHolderActivity, IHolderLifecycle ,IWant {

        private var androidLife: LifecycleProvider<Lifecycle.Event> = AndroidLifecycle.createLifecycleProvider(activity as LifecycleOwner)

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

        override fun runOnUiThread(runnable: Runnable) {
            (activity as? IViewInstance)?.runOnUiThread(runnable)
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

        override fun disconnect() = (activity as? INotifyDataSetChanged)?.disconnect() ?: true

        override fun topViewModelProvider(): ViewModelProvider {
            return activityViewModelProvider
        }

        override fun thisViewModelProvider(): ViewModelProvider {
            return topViewModelProvider()
        }

        override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
            return (activity as? IWant)?.bindToLifecycle() ?: androidLife.bindToLifecycle()
        }

    }


    internal class SupportViewModelFactory(
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
            Log.i("SupportViewModelFactory", "create:   = ${modelClass.name} , activity = ${activity.getHolderActivity()}")
            return InjectionHelp.injectViewModel(activity, defaultArgs, modelClass, handle)!!
        }

    }


    companion object {

        const val BUNDLE_KEY = "support_bundle_key"
        const val ACTIVITY_DESCRIBE_INFO_KEY = "support_activity_key"

        fun <B:ISupprotActivityBusiness> create(
            bundle: Bundle?,
            activity: IHolderContext
        ): B? {


            val checkBusinessClass: (ActivityDescribe) -> Class<B> = {
                InjectionHelp.getClassLoader().loadClass(it.businessName)!! as Class<B>
            }
            val impl = HolderLifecycleImpl(bundle, activity)

            return impl.getArguments()?.let {
                val activityDescribe = it.getParcelable<ActivityDescribe>(ACTIVITY_DESCRIBE_INFO_KEY);
                if (activityDescribe?.businessName != null) {
                    val clazz = checkBusinessClass.invoke(activityDescribe)
                    InjectionHelp.injectBusiness(clazz, impl).apply {
                        Log.i("SupprotActivityBusiness", "create11: 初始化成功 $this")
                    }
                } else {
                    null
                }
            }
        }

        fun create2(bundle: Bundle?, activity: IHolderContext): ISupprotActivityBusiness {
            val impl = HolderLifecycleImpl(bundle, activity)
            return InjectionHelp.injectBusiness(
                SupproActivityBusiness::class.java,
                impl
            ).let {
                Log.i("SupprotActivityBusiness", "create22: 初始化成功 $it")
                it
            }!!
        }

    }

}