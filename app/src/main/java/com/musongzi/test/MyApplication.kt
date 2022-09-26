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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean) {

    }

    override fun getManagers(): Array<ManagerInstanceHelp> {

        return arrayOf(
            com.musongzi.spi.Factory.spiManagerHelp(MyRuleProxy::class.java),
            ConfigManager.ManagerInstanceHelpImpl(),
            instanceHelp {
                RetrofitCallBackInstance()
            },
            instanceOnReady {
                EventBus.getDefault().register(this@MyApplication)
            }
        )
    }

    class RetrofitCallBackInstance : InstanceManager {

        override fun onReady(a: Any?) {
            RetrofitManager.getInstance().init(object : RetrofitManager.CallBack {
                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any? {
                    when (method.name) {
                        "getArrayEngine" -> {
                            var cb: Any?
                            if ((args[0] as Int) > 2) {
                                cb = ObservableCreate.fromArray(emptyArray<StringChooseBean>())
                            } else if ((args[0] as Int) == 2) {
                                cb = ObservableCreate.fromArray(
                                    arrayOf(
                                    "20".bean(),
                                    "21".bean(),
                                    "22".bean(),
                                    "23".bean(),
                                    "24".bean(),
                                    "25".bean(),
                                    "26".bean(),
                                    "27".bean(),
                                    "28".bean()
                                    )
                                )
                            } else {
                                cb = ObservableCreate.fromArray(
                                    arrayOf(
                                        "a".bean(),
                                        "1".bean(),
                                        "2".bean(),
                                        "3".bean(),
                                        "4".bean(),
                                        "5".bean(),
                                        "6".bean(),
                                        "7".bean(),
                                        "8".bean(),
                                        "9".bean(),
                                        "10".bean(),
                                        "11".bean(),
                                        "12".bean(),
                                        "13".bean(),
                                        "14".bean(),
                                        "15".bean(),
                                        "16".bean(),
                                        "17".bean(),
                                        "18".bean(),
                                        "19".bean(),
                                    )

                                )
                            }
                            return cb

                        }
                        "grilPic" -> {
                            return RetrofitManager.getInstance().getApi(Api::class.java).grilPic()
                                .delay(5, TimeUnit.SECONDS).doOnDispose {
                                Log.i(TAG, "grilPic: 取消")
                            }
                        }
                    }
                    return null;
                }

                override fun getOkHttpCLient(): OkHttpClient? = null

                override fun getRetrofit(): Retrofit? = null

            })
        }


    }

}