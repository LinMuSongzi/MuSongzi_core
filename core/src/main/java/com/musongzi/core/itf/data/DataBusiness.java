package com.musongzi.core.itf.data;

import java.util.List;

/**
 * 处理数据
 * @param <I>
 */
public interface DataBusiness<I> {

    void handlerData(List<I> t, int action);

}
