package com.pgt.search.bean;

/**
 * Created by carlwang on 10/31/15.
 */
public class SortBean {
    private String propertyName;
    private String sort = "ASC";

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
