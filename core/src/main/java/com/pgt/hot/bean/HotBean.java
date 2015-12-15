package com.pgt.hot.bean;

import com.pgt.product.bean.Product;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 11/3/15.
 */
public class HotBean implements Serializable {
    static final long serialVersionUID = 1L;
    private Integer hotId;
    private Integer location;
    private Product product;
    private Date beginDate;
    private Date endDate;
    private Integer status;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getHotId() {
        return hotId;
    }

    public void setHotId(Integer hotId) {
        this.hotId = hotId;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }


    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
