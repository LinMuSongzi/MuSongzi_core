package com.musongzi.music.itf

import com.musongzi.core.itf.data.IDataResolve
import com.musongzi.core.itf.page.IDataObservable
import com.musongzi.core.itf.page.ITransformData

/*** created by linhui * on 2022/7/28
 *
 * */
interface RemoteDataPacket<I, D> : IDataObservable<D>, ITransformData<I, D>,
    IDataResolve<I>