package com.musongzi.core.base.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.musongzi.core.itf.ISaveStateHandle
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/*** created by linhui * on 2022/7/27 */
class LocalSavedHandler : ISaveStateHandle {

    private val mRegular : HashMap<String, Any?> = HashMap()
    private val mLiveDatas : HashMap<String, SavingStateLiveData<Any>> = HashMap()

    private fun <T> getLiveDataInternal(
        key: String,
        hasInitialValue: Boolean,
        initialValue: T
    ): MutableLiveData<T> {
        val liveData = mLiveDatas[key] as? MutableLiveData<T>
        if (liveData != null) {
            return liveData
        }
        // double hashing but null is valid value
        val mutableLd = when {
            mRegular.containsKey(key) -> {
                SavingStateLiveData<T>(mRegular, key, mRegular[key] as T)
            }
            hasInitialValue -> {
                SavingStateLiveData<T>(mRegular, key, initialValue)
            }
            else -> {
                SavingStateLiveData<T>(mRegular, key)
            }
        }
        mLiveDatas[key] = mutableLd as SavingStateLiveData<Any>
        return mutableLd
    }

    override fun <T> getLiveData(key: String): MutableLiveData<T> {
       return getLiveDataInternal(key, false, null) as MutableLiveData<T>
    }


    override fun <T> getLiveData(key: String, initialValue: T?): MutableLiveData<T> {
        return getLiveDataInternal(key, true, initialValue) as MutableLiveData<T>
    }

    override fun contains(key: String): Boolean {
        return mRegular.contains(key)
    }

    override fun keys(): Set<String?> {
        val allKeys = HashSet(mRegular.keys)
//        allKeys.addAll(mSavedStateProviders.keys)
        allKeys.addAll(mLiveDatas.keys)
        return allKeys
    }

    override fun <T> get(key: String): T? {
        return mRegular[key] as? T
    }

    override fun <T> remove(key: String): T? {
        val latestValue = mRegular.remove(key) as T?
        val liveData: SavingStateLiveData<*>? = mLiveDatas.remove(key)
        liveData?.detach()
        return latestValue
    }

    override fun <T> set(key: String, value: T?) {
//        validateValue(value)
        val mutableLiveData = mLiveDatas[key] as MutableLiveData<T>?
        if (mutableLiveData != null) {
            // it will set value;
            mutableLiveData.setValue(value)
        } else {
            mRegular[key] = value
        }
    }


    internal class SavingStateLiveData<T> : MutableLiveData<T> {
        private var mKey: String
        private var mHandle: HashMap<String,Any?>? = null

        constructor(handle: HashMap<String,Any?>, key: String, value: T) : super(value) {
            mKey = key
            mHandle = handle
        }

        constructor(handle: HashMap<String,Any?>, key: String) : super() {
            mKey = key
            mHandle = handle
        }

        override fun setValue(value: T?) {
            mHandle?.set(mKey, value)
            super.setValue(value)
        }

        fun detach() {
            mHandle = null
        }
    }


}