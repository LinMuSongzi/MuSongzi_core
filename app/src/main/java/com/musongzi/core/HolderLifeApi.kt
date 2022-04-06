package com.musongzi.core

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.musongzi.core.itf.IRxObserverEasyHelp
import com.musongzi.core.itf.holder.IHolderApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class HolderLifeApi(rxObserverEasyHelp: IRxObserverEasyHelp?) : IHolderApi, InvocationHandler,
    DefaultLifecycleObserver {
    private var rxObserverEasyHelp: IRxObserverEasyHelp? = rxObserverEasyHelp
    private var api: Any? = null


    init {

        rxObserverEasyHelp?.getThisLifecycle()?.lifecycle?.addObserver(this)
        rxObserverEasyHelp?.let {
            val anyRetrofig = it.getRetrofitApiInstance<Any>()
            api = Proxy.newProxyInstance(
                rxObserverEasyHelp.javaClass.classLoader,
                anyRetrofig.javaClass.interfaces,
                this
            )
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        api = null
        rxObserverEasyHelp = null
    }

    override fun getApi(): Any? {
        return api
    }

    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        return findMethodInvoke(
            rxObserverEasyHelp?.getRetrofitApiInstance<Any>(),
            proxy,
            method,
            args
        )
    }

    private fun findMethodInvoke(
        retrofitApiInstance: Any?,
        proxy: Any,
        method: Method,
        args: Array<Any>
    ): Any? {
        var methodInstance = method.invoke(retrofitApiInstance, args)
        if (method.returnType.isAssignableFrom(Observable::class.java)) {
            methodInstance = (methodInstance as Observable<*>).compose(simpleCompose())
            rxObserverEasyHelp?.let {
                methodInstance.compose(it.bindToLifecycle())
            }
        }
        return methodInstance
    }

    private fun <T> simpleCompose(): ObservableTransformer<T, T> = ObservableTransformer<T, T> {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}