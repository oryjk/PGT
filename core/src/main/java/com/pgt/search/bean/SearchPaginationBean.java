package com.pgt.search.bean;

import com.pgt.utils.PaginationBean;

import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 10/31/15.
 */
public class SearchPaginationBean extends PaginationBean {

    private String categoryId;
    private int stock;
    private List<SortBean> sortBeans;
    private String term;
    private Boolean isNew;
    private Integer priceStart;
    private Integer priceEnd;

    public Integer getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Integer priceStart) {
        this.priceStart = priceStart;
    }

    public Integer getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Integer priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<SortBean> getSortBeans() {
        return sortBeans;
    }

    public void setSortBeans(List<SortBean> sortBeans) {
        this.sortBeans = sortBeans;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
