package com.musongzi.core.itf.page;

import java.util.List;

/**
 * 处理数据
 * @param <I>
 */
public interface OnPageDataChange<I> {

    void handlerDataChange(List<I> t, int action);

}

