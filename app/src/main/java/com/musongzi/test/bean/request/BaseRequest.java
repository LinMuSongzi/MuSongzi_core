package com.musongzi.test.bean.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.musongzi.core.base.manager.ManagerUtil;
import com.musongzi.test.bean.IRequest;

/*** created by linhui * on 2022/10/17 */
public class BaseRequest implements IRequest {

    @Nullable
    protected String requestUrl = null;
    @Nullable
    protected String token = null;
    @Nullable
    protected String userFlag = null;

    public BaseRequest() {
//        token = ManagerUtil.INSTANCE.manager()
    }

    public BaseRequest(@NonNull String token, @NonNull String userFlag) {
        this.token = token;
        this.userFlag = userFlag;
    }

    public void setRequestUrl(@Nullable String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }

    @Nullable
    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(@Nullable String userFlag) {
        this.userFlag = userFlag;
    }

    @Override
    public String getRequestUrl() {
        return userFlag;
    }
}
