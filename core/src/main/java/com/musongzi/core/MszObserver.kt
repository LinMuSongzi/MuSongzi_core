package com.musongzi.core

import android.util.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

class MszObserver<T>(var c: Consumer<T>) : Observer<T> {

    companion object {
        const val TAG = "MszObserver"
    }

    override fun onSubscribe(d: Disposable?) {
        Log.i(TAG, "onSubscribe: " + Thread.currentThread())
    }

    override fun onNext(t: T) {
//        Log.i(TAG, "onNext: Thread = " + Thread.currentThread() + " : " + t)
        c.accept(t)
    }

    override fun onError(e: Throwable?) {
        Log.i(TAG, "onError: " + e?.message)
    }

    override fun onComplete() {
        Log.i(TAG, "onComplete: " + Thread.currentThread())
    }


}