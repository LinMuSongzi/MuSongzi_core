package com.musongzi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

val mExecutor: ExecutorService = Executors.newFixedThreadPool(10)
const val TAG: String = "FutureDemo"

/*** created by linhui * on 2022/8/22 */
@RequiresApi(Build.VERSION_CODES.N)
object FutureDemo {

    /**
     * 依赖关系
     * 用来连接两个有依赖关系的任务，结果由第二个任务返回
     */
    fun thenCompose() {
        val r = Runnable {
            val sum = CompletableFuture.supplyAsync {
//                Thread.sleep(3000)
                1
            }.thenCompose {
                CompletableFuture.supplyAsync {
                    Thread.sleep(2000)
                    it + 2
                }
            }.get()
            Log.i(TAG, " thenCompose $sum")
        }
        mExecutor.execute(r)
    }

    /**
     * 依赖关系
     * 把前面任务的执行结果，交给后面的Function
     */
    fun thenApply() {

        val r = Runnable {
            val sum = CompletableFuture.supplyAsync {
//                Log.i(TAG, "thread: ${Thread.currentThread().name}")
                1
            }.thenApply {
//                Log.i(TAG, "thread: ${Thread.currentThread().name}")
                Thread.sleep(2500)
                it + 2
            }.get()
//            Log.i(TAG, "thread: ${Thread.currentThread().name}") /
            Log.i(TAG, " thenApply $sum")
        }
        mExecutor.execute(r)
    }


    /**
     * and集合关系
     * 合并任务，有返回值
     */

    fun thenCombine() {
        val r = Runnable {
            val sum = CompletableFuture.supplyAsync {
                Thread.sleep(2000)
                1
            }.thenCombine(CompletableFuture.supplyAsync {
                Thread.sleep(3000)
                2
            }) { t, u ->
//                Log.i(TAG, " $t + $u")
                t + u
            }.get()
            Log.i(TAG, " thenCombine $sum")
        }
        mExecutor.execute(r)
    }

    /**
     * and集合关系
     * 两个任务执行完成后，将结果交给thenAccepetBoth处理，无返回值
     */
    fun thenAcceptBoth() {
        val r = Runnable {
            CompletableFuture.supplyAsync {
                Thread.sleep(5000)
                1
            }.thenAcceptBoth(CompletableFuture.supplyAsync {
                Thread.sleep(3200)
                2
            }) { i, t ->
                Log.i(TAG, " thenAcceptBoth ${i + t}")
            }.get()
        }
        mExecutor.execute(r)
    }

    /**
     * and集合关系
     * 两个任务都执行完成后，执行下一步操作(Runnable类型任务)
     */
    fun runAfterBoth() {
        val r = Runnable {
            CompletableFuture.supplyAsync {
                Thread.sleep(3500)
                1
            }.runAfterBoth(CompletableFuture.supplyAsync {
                Thread.sleep(500)
                2
            }) {
                Log.i(TAG, " runAfterBoth 应该是3哈哈")
            }.get()
        }
        mExecutor.execute(r)
    }

    /**
     * or聚合关系
     * 两个任务哪个执行的快，就使用哪一个结果，有返回值
     */
    fun applyToEither() {
        val r = Runnable {
            val one = Math.random() * 2000
            val two = Math.random() * 2000
            Log.i(TAG, " applyToEither start one = $one , two = $two")
            val sum = CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                one
            }.applyToEither(CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }) {
                it.toLong()
            }.get()
            Log.i(TAG, " applyToEither $sum")
        }
        mExecutor.execute(r)

    }

    /**
     * or聚合关系
     *两个任务哪个执行的快，就消费哪一个结果，无返回值
     */
    fun acceptEither() {
        val r = Runnable {
            val one = Math.random() * 2000
            val two = Math.random() * 2000
            Log.i(TAG, " acceptEither start one = $one , two = $two")
            val sum = CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                one
            }.acceptEither(CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }) {

                Log.i(TAG, " acceptEither ${it.toLong()}")
            }.get()
            Log.i(TAG, " acceptEither $sum")
        }
        mExecutor.execute(r)
    }


    /**
     * or聚合关系
     * 任意一个任务执行完成，进行下一步操作(Runnable类型任务)
     */
    fun runAfterEither() {
        val r = Runnable {
            val one = Math.random() * 2000
            val two = Math.random() * 2000
            Log.i(TAG, " runAfterEither start one = $one , two = $two")
            CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                one
            }.runAfterEither(CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }) {
                Log.i(TAG, " runAfterEither ")
            }.get()
        }
        mExecutor.execute(r)
    }


    /**
     * 并行执行
     * 当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
     */
    fun allOf() {
        val r = Runnable {

            val sum = AtomicInteger(0)
            CompletableFuture.allOf(CompletableFuture.supplyAsync({
                Thread.sleep(1000)
                sum.set(sum.get().plus(1))
            }, mExecutor), CompletableFuture.supplyAsync {
                Thread.sleep(2500)
                sum.set(sum.get().plus(2))
            }, CompletableFuture.supplyAsync({
                Thread.sleep(500)
                sum.set(sum.get().plus(3))
            }, mExecutor)).get()
            Log.i(TAG, "allOf: ${sum.get()}")
        }
        mExecutor.execute(r)
    }

    /**
     * 并行执行
     * 当任何一个给定的CompletablFuture完成时，返回一个新的CompletableFuture
     */
    fun anyOf() {
        val r = Runnable {
            val one = Math.random() * 3000
            val two = Math.random() * 3000
            val three = Math.random() * 3000
            Log.i(TAG, " anyOf: one = $one , two = $two , three = $three")
            val sum = CompletableFuture.anyOf(CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                one
            }, CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }, CompletableFuture.supplyAsync {
                Thread.sleep(three.toLong())
                three
            }).get()
            Log.i(TAG, "anyOf: $sum")
        }
        mExecutor.execute(r)
    }

    /**
     * 并行执行
     * 当任何一个给定的CompletablFuture完成时，返回一个新的CompletableFuture
     */
    fun whenComplete() {
        val r = Runnable {

            val one = Math.random() * 3000
            val two = Math.random() * 3000
            val three = Math.random() * 3000
            Log.i(TAG, " whenComplete: one = $one , two = $two , three = $three")
            val sum = CompletableFuture.anyOf(CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                (one as Int)
                one
            }, CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }, CompletableFuture.supplyAsync {
                Thread.sleep(three.toLong())
                three
            }).whenComplete { r, e ->
                Log.i(TAG, "whenComplete: result = $r , exception = $e")
            }.get()
            Log.i(TAG, "whenComplete: $sum")
        }
        mExecutor.execute(r)
    }




    /**
     * 返回一个新的CompletableFuture，当前面的CompletableFuture完成时，它也完成，当它异常完成时，给定函数的异常触发这个CompletableFuture的完成
     */
    fun exceptionally() {
        val r = Runnable {

            val one = Math.random() * 3000
            val two = Math.random() * 3000
            val three = Math.random() * 3000
            Log.i(TAG, " exceptionally: one = $one , two = $two , three = $three")
            val sum = CompletableFuture.anyOf(CompletableFuture.supplyAsync {
                Thread.sleep(one.toLong())
                (one as Int)
                one
            }, CompletableFuture.supplyAsync {
                Thread.sleep(two.toLong())
                two
            }, CompletableFuture.supplyAsync {
                Thread.sleep(three.toLong())
                three
            }).exceptionally {
                Log.i(TAG, "exceptionally: exception = $it")
                "end"
            }.get()
            Log.i(TAG, "exceptionally: $sum")
        }
        mExecutor.execute(r)

    }

}