package com.musongzi.core.itf.data;

import java.util.List;

/**
 * 处理数据
 * @param <ENTITY>
 */
public interface DataBusiness<ENTITY> {

    void handlerData(List<ENTITY> t, int action);

}
