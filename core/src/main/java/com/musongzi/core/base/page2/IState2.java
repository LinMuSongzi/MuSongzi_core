package com.musongzi.core.base.page2;

import com.musongzi.core.itf.page.IPageEngine;

public interface IState2 {

    int ON_SUBSCRIBE_OBSERVABLE_STATE = 600;
    int ON_ERROR_BY_SUBSCRIBE_STATE = 404;

    int ON_COMPLETE_BY_SUBSCRIBE_STATE = 200;
    int NO_MORE_PAGE = IPageEngine.NO_MORE_PAGE;
    int STATE_END_REFRASH = IPageEngine.STATE_END_REFRASH;
    int STATE_END_NEXT = IPageEngine.STATE_END_NEXT;
    int NO_MORE_BY_LOADED_SUCCED_PAGE = IPageEngine.NO_MORE_BY_LOADED_SUCCED_PAGE;
    int LOADED_SUCCED_PAGE = IPageEngine.LOADED_SUCCED_PAGE;
    int STATE_CLEAR = IPageEngine.STATE_CLEAR;
    int STATE_START_REFRASH = IPageEngine.STATE_START_REFRASH;
    int STATE_START_NEXT = IPageEngine.STATE_START_NEXT;


    void setStateInfo(StateInfo stateInfo);

}
