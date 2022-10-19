package com.musongzi.core

import android.util.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

class BaseObserver<T : Any>(var c: Consumer<T>? = null) : Observer<T> {

    companion object {
        const val TAG = "BaseObserver"
    }

    var runOnDisposable: (Disposable.() -> Unit)? = null

    override fun onSubscribe(d: Disposable) {
        runOnDisposable?.invoke(d)
        Log.i(TAG, "onSubscribe: " + Thread.currentThread())
    }

    override fun onNext(t: T) {
        Log.i(TAG, "onNext: Thread = " + Thread.currentThread() + " : " + t)
        c?.accept(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
//        Log.i(TAG, "onError: " + e?.message)
    }

    override fun onComplete() {
        Log.i(TAG, "onComplete: " + Thread.currentThread())
    }


}