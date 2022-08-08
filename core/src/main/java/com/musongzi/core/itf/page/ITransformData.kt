package com.musongzi.core.itf.page

/*** created by linhui * on 2022/7/28 */
interface ITransformData<I,D> {

    /**
     * 核心的数据转换的函数
     *
     * @param entity 当前加载的数据
     * @return 返回数据源对应泛型
     */
    fun transformDataToList(entity: D): List<I>
}