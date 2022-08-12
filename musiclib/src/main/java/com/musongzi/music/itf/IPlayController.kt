package com.musongzi.music.itf

/*** created by linhui * on 2022/7/28
 *
 * 一个媒体播放器的控制器
 * 可以控制播放的各种行为
 *
 * tip：控制器的行为可能只是基于某个音乐队列,比如 [IPlayQueueManager.getPlayController] 获取到的是当前音乐队列/专辑的控制器
 *     也有可能只是一个简单的行为，不在乎是哪个业务里。
 *
 * */
interface IPlayController : IPlayObservable {

    /**
     * 播放当前媒体信息
     * @return 基于[IMediaPlayInfo] 支持多种类型
     */
    fun playMusicByInfo(entity: IMediaPlayInfo)

    /**
     * 播放当前媒体信息
     * @return 只支持http方式
     */
    fun playMusic(stringUrl: String)

    /**
     * 暂停正在播放的媒体，
     */
    fun pauseMusic()

    /**
     * 停止媒体播放，重置播放器
     */
    fun stopMusic()

    companion object {
        /**
         * 播放前获取到当前媒体信息是回调状态
         */
        const val ON_INFO = "ON_INFO"

        /**
         * 开始播放媒体时的开始回调状态
         */
        const val ON_START = "ON_START"

        /**
         * 媒体播放完毕时的回调状态
         */
        const val ON_COMPLETE = "ON_COMPLETE";

        /**
         * 媒体暂停时的回调状态
         */
        const val ON_PAUSE = "ON_PAUSE"

        /**
         * 媒体播放停止时的回调状态
         */
        const val ON_STOP = "ON_STOP"

        /**
         * 当媒体正在缓存时的回调
         */
        const val ON_BUFFER = "ON_BUFFER"

        /**
         * 当媒体正在播放时的回调（每500ms回调一次）
         */
        const val ON_PLAYING = "ON_PLAYING"
    }


}