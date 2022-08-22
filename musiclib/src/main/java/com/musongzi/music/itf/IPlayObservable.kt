package com.musongzi.music.itf

import com.musongzi.core.itf.ILifeObject

/*** created by linhui * on 2022/7/28
 *
 * 一个媒体被观察者接口
 * 可被观察基于生命周期
 *
 * */
interface IPlayObservable {

    /**
     * 观察媒体播放不同状态
     * @param life 可观察的生命周期对象
     * @param p 回调函数
     */
    fun observerState(life: ILifeObject?, p:PlayMediaObervser);

}