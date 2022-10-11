package com.musongzi.test.bean;

public class LoginBean {

    private String pwd;
    private String user;

    public LoginBean() {
    }

    public LoginBean(String user, String pwd ) {
        this.pwd = pwd;
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
