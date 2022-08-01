package com.musongzi.music.itf

import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
interface IMusicArray :IAttributeArray<MusicPlayInfoImpl,Any>,IHolderPlayController {
    fun thisPlayIndex():Int
    fun changeThisPlayIndex(index:Int)
    fun changeThisPlayIndexAndAdd(stringUrl: String)
}