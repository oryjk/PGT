package com.pgt.search.bean;

/**
 * Created by carlwang on 10/31/15.
 */
public class SearchBean {

    private String searchKey;
    private String dateSort;
    private String priceSort;
    private boolean inStack;
    private String categoryId;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getDateSort() {
        return dateSort;
    }

    public void setDateSort(String dateSort) {
        this.dateSort = dateSort;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }

    public boolean isInStack() {
        return inStack;
    }

    public void setInStack(boolean inStack) {
        this.inStack = inStack;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
