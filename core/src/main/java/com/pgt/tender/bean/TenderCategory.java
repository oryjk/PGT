package com.pgt.tender.bean;

import java.io.Serializable;

/**
 * Created by carlwang on 1/20/16.
 */
public class TenderCategory implements Serializable{
    private Integer id;
    private Integer tenderId;
    private Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
