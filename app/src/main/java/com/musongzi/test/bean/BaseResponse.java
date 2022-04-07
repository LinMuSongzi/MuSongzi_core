package com.musongzi.test.bean;

public class BaseResponse {

    private String _url;

    private Object[] requestParamter;

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        this._url = url;
    }

    public Object[] getRequestParamter() {
        return requestParamter;
    }

    public void setRequestParamter(Object[] requestParamter) {
        this.requestParamter = requestParamter;
    }

    private int code;

    private String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    getData ();

}
