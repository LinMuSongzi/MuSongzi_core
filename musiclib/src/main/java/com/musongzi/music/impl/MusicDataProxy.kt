package com.musongzi.music.impl

import com.musongzi.core.itf.data.DataBusiness
import com.musongzi.core.itf.page.IDataObservable
import com.musongzi.core.itf.page.IHolderPageEngine
import com.musongzi.core.itf.page.ITransformData
import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
abstract class MusicDataProxy<I,D>:IDataObservable<D> ,ITransformData<I,D>,IHolderPageEngine<I>,
    DataBusiness<I> {
}