package com.musongzi.music.itf

import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28
 *
 * 媒体状态观察者
 *
 * */
interface PlayMediaObervser {


    /**
     * 媒体不同状态回调函数
     * @param state 当前的观察的状态，具体详见于[IPlayController]
     * @param info 当前观察的媒体信息
     * @param player 播放器核心具体实现类 ,默认为当前框架自己实现的实例，具体详见于[IMusicInit.buildPlayerManagerType]
     * @param other 其他媒体信息，比如[IPlayController.ON_BUFFER] 的时候是缓冲的字节[Byte]
     */
    fun onStateChange(state: String, info: IMediaPlayInfo?, player: Any? = null, other: Any? = null)

}