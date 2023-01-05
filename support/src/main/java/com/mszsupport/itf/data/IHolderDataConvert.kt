package com.mszsupport.itf.data

import com.mszsupport.itf.page.IDataEngine
import com.mszsupport.itf.page.ITransformData


interface IHolderDataConvert<E, D> : IDataEngine<D>, DataBusiness<E>,
    ITransformData<E, D> {




}