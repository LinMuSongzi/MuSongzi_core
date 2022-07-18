package com.musongzi.comment.ad

/*** created by linhui * on 2022/7/18 */
interface IAdController {

    fun <A> loadAd(action: A);

    fun getLoadConfig(): AdSimpleConfig

    fun setLoadConfig(adConfig: AdSimpleConfig)

    fun setAdListener(listener: AdListener)

    interface AdListener {

        fun onAdLoad()

        fun onAdError(code: Int, message: Any, action: Int)

        fun onAdLoaderSucced(adEntity: Any, code: Int, action: Int)

        fun onLoadTimeOut(message: Any, action: Int)

        fun onCancelLoad();

        @Deprecated("暂定")
        fun onLoadedButViewFinish();

    }


}