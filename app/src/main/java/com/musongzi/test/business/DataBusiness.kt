package com.musongzi.test.business

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import java.lang.ref.WeakReference
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/*** created by linhui * on 2022/8/24 */
abstract class DataBusiness<T> {

    companion object {
        const val NORMAL_FLAG = 0
        const val HAD_LOADED = 1
    }

    private val mExecutor: ExecutorService = Executors.newCachedThreadPool()
    private val flag = AtomicInteger(NORMAL_FLAG)
    private val tabs = MutableLiveData<T>().also {
        asyInit()
    }
    private val cacheOnNoInitArray = HashSet<Pair<LifecycleOwner,Observer<T>>>()

    fun observer(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        val tabs = getTabs()
        if(tabs == null){
            val cacheOnNoInitArrayReference = WeakReference(cacheOnNoInitArray)
            lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                val pair = lifecycleOwner to observer
                override fun onCreate(owner: LifecycleOwner) {
                    cacheOnNoInitArrayReference.get()?.add(pair)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    cacheOnNoInitArrayReference.get()?.remove(pair)
                }

            })
        }else{
            tabs.observe(lifecycleOwner,observer)
        }
    }

    private fun addAllCacheObserver() {
        if (cacheOnNoInitArray.size != 0) {
            for(p in cacheOnNoInitArray) {
                tabs.observe(p.first,p.second)
            }
            cacheOnNoInitArray.clear()
        }
    }

    fun getTabs(): LiveData<T>? {
        if (flag.get() == HAD_LOADED) {
            return tabs
        }
        return null
    }

    private fun asyInit() {
        mExecutor.execute {
            val value = if (Build.VERSION.SDK_INT > 23) {
                CompletableFuture.supplyAsync({
                    loadData()
                }, mExecutor).get()
            } else {
                val task = FutureTask {
                    loadData()
                }
                mExecutor.execute(task)
                task.get()
            }
            runOnUiThread {
                addAllCacheObserver()
                flag.set(HAD_LOADED)
                tabs.value = value
            }
        }
    }

    /**
     * 此方法已经是异步
     */
    protected abstract fun loadData(): T

    fun runOnUiThread(run: Runnable) {
        Handler(Looper.getMainLooper()).post(run)
    }

}