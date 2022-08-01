package com.musongzi.music.impl

import com.musongzi.core.itf.page.IDataObservable
import com.musongzi.core.itf.page.ITransformData
import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
abstract class MusicDataProxy<D>:IDataObservable<D> ,ITransformData<MusicPlayInfoImpl,D>{
}