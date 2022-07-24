package com.musongzi.comment.bean;

import com.musongzi.comment.bean.response.BaseItemBeanImpl;

/*** created by linhui * on 2022/7/22 */
public class FileBean extends BaseItemBeanImpl {



    String path;

    public FileBean() {
    }


    public FileBean(FileBean fileBean){
        path = fileBean.path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileBean(String path) {
        this.path = path;
    }
}
