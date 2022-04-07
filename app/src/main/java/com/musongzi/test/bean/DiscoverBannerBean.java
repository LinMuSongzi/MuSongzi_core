package com.musongzi.test.bean;

import java.io.Serializable;

public class DiscoverBannerBean implements Serializable {


    private String bannerPic;
    private String clickUrl;
    private String name;
    private int order;
    private int bannerType;
    private String status;

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int type) {
        this.bannerType = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
