package com.musongzi.core.base.page2;

import java.util.List;

public interface OnPageDataChange2<I, A> {

    void handlerDataChange(List<I> datas, A other);

}
