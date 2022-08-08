package com.musongzi.test.bean

import com.musongzi.core.itf.IAttribute
import com.musongzi.music.bean.BaseAttribute

/*** created by linhui * on 2022/8/3 */
open class SongArrayInfo : BaseAttribute() {

    lateinit var name:String
    var creatTime :Long = System.currentTimeMillis()


}