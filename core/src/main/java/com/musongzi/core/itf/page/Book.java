package com.musongzi.core.itf.page;

/**
 * 书本目录的接口
 */
public interface Book {

    /**
     * 当前一页加载的数据
     * @return
     */
    int pageSize();

    /**
     * 从哪一页开始
     * @return
     */
    int thisStartPage();

}
