package com.musongzi.music.itf

/*** created by linhui * on 2022/7/28 */
interface IPlayController {


    fun playMusic(stringUrl: String)

    fun pauseMusic()

    fun stopMusic()

    fun observerState();

}