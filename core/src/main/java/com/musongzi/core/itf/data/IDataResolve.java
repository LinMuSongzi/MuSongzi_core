package com.musongzi.core.itf.data;

import java.util.List;

/**
 * 处理数据
 * @param <I>
 */
public interface IDataResolve<I> {

    void resolveData(List<I> t, int action);

}
