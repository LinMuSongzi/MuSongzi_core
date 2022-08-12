package com.musongzi.music.itf

import com.musongzi.music.itf.small.OnPlayCompleteListener
import com.musongzi.music.itf.small.OnPlayMomentListener
import com.musongzi.music.itf.small.OnPlayCountListener
import com.musongzi.music.itf.small.OnReadyListener

/*** created by linhui * on 2022/7/28
 *
 * 媒体状态观察者 2
 *
 * 实现了其他应接口隔离而分开的其他接口
 * 是一个大集合
 *
 * */
interface PlayMusicObervser2 : OnReadyListener, OnPlayCompleteListener, OnPlayMomentListener,
    OnPlayCountListener

