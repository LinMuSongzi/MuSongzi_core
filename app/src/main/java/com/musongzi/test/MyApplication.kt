package com.musongzi.test

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.RequestManager
import com.musongzi.ConfigManager
import com.musongzi.core.ExtensionCoreMethod.bean
import com.musongzi.core.StringChooseBean
import com.musongzi.core.base.MszApplicaton
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.base.manager.InstanceManager
import com.musongzi.core.base.manager.ManagerInstanceHelp
import com.musongzi.core.base.manager.ManagerInstanceHelp.Companion.instanceHelp
import com.musongzi.core.base.manager.ManagerInstanceHelp.Companion.instanceOnReady
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.util.WriteTxt
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.event.IMusicEvent
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Retrofit
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashSet

class MyApplication : MszApplicaton() {
    companion object {
        const val TAG = "MyApplication"
        const val URL2 = "http://192.168.1.106:8080/"
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean) {
        resources.getStringArray(R.array.manager_instance)
    }

    override fun getManagers(): Array<ManagerInstanceHelp> {

        return arrayOf(
            com.musongzi.spi.Factory.spiManagerHelp(MyRuleProxy::class.java),
//            ConfigManager.ManagerInstanceHelpImpl(),
            instanceHelp {
                RetrofitCallBackInstance()
            },
            instanceOnReady {
                EventBus.getDefault().register(this@MyApplication)
            }
        )
    }

//    class RetrofitCallBackManagerHelp:ManagerInstanceHelp{
//
//    }

    class RetrofitCallBackInstance : InstanceManager {

        override fun onReady(a: Any?) {
            RetrofitManager.getInstance().init(object : RetrofitManager.CallBack {
                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any? {
                    when (method.name) {
                        "grilPic" -> {
                            return RetrofitManager.getInstance().getApi(MszTestApi::class.java).grilPic()
                                .delay(5, TimeUnit.SECONDS).doOnDispose {
                                Log.i(TAG, "grilPic: 取消")
                            }
                        }
                    }
                    return null;
                }

                override fun baseUrl() =  URL2

            })
        }


    }

}