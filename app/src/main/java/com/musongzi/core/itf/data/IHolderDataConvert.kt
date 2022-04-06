package com.musongzi.core.itf.data

import com.musongzi.core.itf.page.IDataEngine


interface IHolderDataConvert<ENTITY, DATA> : IDataEngine<DATA>, DataBusiness<ENTITY> {


    /**
     * 核心的数据转换的函数
     *
     * @param entity 当前加载的数据
     * @return 返回数据源对应泛型
     */
    fun transformDataToList(entity: DATA): List<ENTITY>

}