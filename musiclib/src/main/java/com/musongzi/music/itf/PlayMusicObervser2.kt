package com.musongzi.music.itf

import com.musongzi.music.itf.small.OnPlayCompleteListener
import com.musongzi.music.itf.small.OnPlayChangeListener
import com.musongzi.music.itf.small.OnPlayCountListener
import com.musongzi.music.itf.small.OnReadyListener

/*** created by linhui * on 2022/7/28 */
interface PlayMusicObervser2 : OnReadyListener, OnPlayCompleteListener, OnPlayChangeListener,
    OnPlayCountListener {


}