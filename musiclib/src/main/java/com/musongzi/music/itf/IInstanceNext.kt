package com.musongzi.music.itf

import com.musongzi.core.itf.INeed

/*** created by linhui * on 2022/7/29 */
interface IInstanceNext {


   fun <I> create(instanceClass:Class<I>,any: Any):I

}