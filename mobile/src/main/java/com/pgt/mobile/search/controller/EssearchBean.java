package com.pgt.mobile.search.controller;

/**
 * Created by xiaodong on 15-12-24.
 */
public class EssearchBean {

    private String categoryId;
    private String term;
    private String sortKey;
    private String sortOrder;
    private String priceStart;
    private String priceEnd;
    private String currentIndex;
    private String mobileCapacity;


    public String getMobileCapacity() {
        return mobileCapacity;
    }

    public void setMobileCapacity(String mobileCapacity) {
        this.mobileCapacity = mobileCapacity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(String priceStart) {
        this.priceStart = priceStart;
    }

    public String getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(String priceEnd) {
        this.priceEnd = priceEnd;
    }

    public String getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(String currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
