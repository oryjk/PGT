package com.pgt.inventory;

import java.util.Date;

/**
 * Created by samli on 2015/12/21.
 */
public class InventoryLock {
    private Long orderId;
    private Long produdctId;
    private int quantity;
    private Date expiredDate;
    private Date creationDate;
    private Date updateDate;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProdudctId() {
        return produdctId;
    }

    public void setProdudctId(Long produdctId) {
        this.produdctId = produdctId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
