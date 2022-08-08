package com.musongzi.music.itf

import com.musongzi.core.itf.ILifeObject

/*** created by linhui * on 2022/7/28 */
interface IPlayObsavable {

    fun observerState(life: ILifeObject?, p:PlayMusicObervser);

}