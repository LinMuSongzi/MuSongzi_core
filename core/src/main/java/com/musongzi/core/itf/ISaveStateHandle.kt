package com.musongzi.core.itf

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData

/*** created by linhui * on 2022/7/13 */
interface ISaveStateHandle {
    @MainThread
    fun <T> getLiveData(key: String): MutableLiveData<T>

    @MainThread
    fun contains(key: String): Boolean

    @MainThread
    fun <T> getLiveData(key: String, @SuppressLint("UnknownNullness") initialValue: T?): MutableLiveData<T>

    @MainThread
    fun keys(): Set<String?>

    @MainThread
    operator fun <T> get(key: String): T?

    @MainThread
    @Nullable
    fun <T> remove(key: String): T?

    @MainThread
    operator fun <T> set(key: String, value: T?)

}