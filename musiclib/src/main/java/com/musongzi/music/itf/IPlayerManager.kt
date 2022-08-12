package com.musongzi.music.itf

/*** created by linhui * on 2022/7/28
 *
 * 一个播放器管理者
 * 公开监听播放状态
 * 实现了控制器播放媒体
 * */
interface IPlayerManager : IPlayController {

    fun observerState(p: PlayMediaObervser);

}