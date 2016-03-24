package com.pgt.base.bean;

/**
 * Created by carlwang on 3/13/16.
 */

public enum MyAccountNavigationEnum {
    MY_ORDER("我的订单", ""), MY_ASSET("我的资产", ""), MY_FAVORITE("我的收藏", "/myAccount/favourites?type=6"), MY_RECENT_VIEW("我的浏览", "/myAccount/browsedProducts"), MY_INFO("我的信息", ""),
    MY_ADDRESS("我的地址", "/my-account/person-info/address"),
    CHANGE_PASSWORD("修改密码", "/user/resetPassword");

    private String label;

    private String url;
    MyAccountNavigationEnum(String label, String url) {
        this.label = label;
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
