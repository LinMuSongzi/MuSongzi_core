package com.musongzi.test

import android.util.Log
import androidx.databinding.DataBinderMapperImpl
import androidx.databinding.DataBindingUtil
import androidx.multidex.MultiDexApplication
import com.musongzi.core.ExtensionCoreMethod.bean
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.base.manager.ManagerUtil
import com.musongzi.core.base.manager.RetrofitManager
import com.musongzi.core.util.WriteTxt
import com.musongzi.music.impl.Factory
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.business.MusicConfigHelpBusines
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

class MyApplication : MultiDexApplication() {
    companion object {
        const val TAG = "MyApplication"
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean) {

    }

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.i(TAG, "message: t = ${e.message}")
            for (error in e.stackTrace) {
                Log.i(TAG, "error: $error")
            }
            WriteTxt.exception(e)
        }

        ManagerUtil.init(arrayListOf(Factory.buildInstanceManagerHelp {
            MusicConfigHelpBusines().apply {
                afterHandlerBusiness()
            }
        }), classLoader)

        EventBus.getDefault().register(this)
        registerEvent(ILoginEvent::class.java) {
            object : IMusicEvent {
                override fun play() {
                    Log.i(TAG, "initEvent play: MyApplication")
                }

                override fun onLogin() {
                    Log.i(TAG, "initEvent onLogin: MyApplication")
//                    Log.i(TAG, "onLogin: MyApplication ")
                }

                override fun onLogout() {
                    Log.i(TAG, "initEvent onLogout: MyApplication ")
                }

            }
        }

        Thread.UncaughtExceptionHandler { _, e ->
            Log.i(TAG, "onCreate: " + e.message + " , " + Arrays.toString(e.stackTrace) + "\n");
        }
        RetrofitManager.getInstance().init(object : RetrofitManager.CallBack {
            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any? {
                when (method.name) {
                    "getArrayEngine" -> {
                        var cb: Any? = null
                        if ((args[0] as Int) > 1) {
                            cb = ObservableCreate.fromArray(emptyArray<String>())
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
                                    "ad".bean(),
                                    "1".bean(),
                                    "2".bean(),
                                    "3".bean(),
                                    "4".bean(),
                                    "5".bean(),
                                    "6".bean(),
                                    "ad".bean()
                                )

                            )
                        }
                        return cb
                    }
                }
                return null;
            }

            override fun getOkHttpCLient(): OkHttpClient? = null

            override fun getRetrofit(): Retrofit? = null

        })
    }


}