package com.musongzi.core.itf.data

import com.musongzi.core.itf.page.IDataEngine
import com.musongzi.core.itf.page.ITransformData
import com.musongzi.core.itf.page.OnPageDataChange


interface IHolderDataConvert<E, D> : IDataEngine<D>,
    OnPageDataChange<E>,
    ITransformData<E, D>