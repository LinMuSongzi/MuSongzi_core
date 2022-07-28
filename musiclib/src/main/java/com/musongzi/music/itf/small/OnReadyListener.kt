package com.musongzi.music.itf.small

import com.musongzi.music.bean.MediaConfig
import com.musongzi.music.bean.MediaPlayInfo

/*** created by linhui * on 2022/7/28 */
interface OnReadyListener {
    fun onReady(info: MediaPlayInfo, config: MediaConfig)
}