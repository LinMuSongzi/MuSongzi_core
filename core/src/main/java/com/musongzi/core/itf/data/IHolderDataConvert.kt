package com.musongzi.core.itf.data

import com.musongzi.core.itf.page.IDataEngine
import com.musongzi.core.itf.page.ITransformData


interface IHolderDataConvert<E, D> : IDataEngine<D>, DataBusiness<E>,
    ITransformData<E, D> {




}