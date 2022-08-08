package com.musongzi.music.impl

import com.musongzi.core.itf.data.DataBusiness
import com.musongzi.core.itf.page.IDataObservable
import com.musongzi.core.itf.page.IHolderPageEngine
import com.musongzi.core.itf.page.ILimitOnLoaderState
import com.musongzi.core.itf.page.ITransformData
import com.musongzi.music.bean.MusicPlayInfoImpl

/*** created by linhui * on 2022/7/28 */
 interface MusicDataProxy<I,D>:IDataObservable<D> ,ITransformData<I,D>,//,IHolderPageEngine<I>,
    DataBusiness<I> {


//    override fun enableRefreshLimit(enable: Boolean) {
//        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableRefreshLimit(enable)
//    }
//
//    override fun enableMoreLoadLimit(enable: Boolean) {
//        (getHolderPageEngine() as? ILimitOnLoaderState)?.enableMoreLoadLimit(enable)
//    }



}