package com.musongzi.core.itf.page;

/*** created by linhui * on 2022/6/29
 * 新增扩展，针对某些情况需要快速刷新的时候
 *
 * */
public interface ILimitRead extends IRead {

    /**
     * 刷新不需要限制
     */
    void refreshNoLimite();

    /**
     * 加载更多不需要限制
     */
    void nextNoLimite();

}
