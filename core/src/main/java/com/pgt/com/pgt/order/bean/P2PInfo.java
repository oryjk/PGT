package com.pgt.com.pgt.order.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 1/16/16.
 */
public class P2PInfo implements Serializable {
    private Integer id;
    private Integer type;
    private Integer pawnShopId;
    private Integer pawnShopOwnerId;
    private Integer tenderId;
    private Date expectDueDate;
    private Date actualDueDate;
    private Double expectIncoming;
    private Double actualIncoming;
    private Date publishDate;
    private Integer prePeriod;
    private Integer postPeriod;
    private int placeQuantity;
    private Double unitPrice;
    private Double handlingFee;
    private Double interestRate;
    private Double handlingFeeRate;
    private Date payTime; // TODO DB STRUCTURE
    private Date creationDate;
    private Date updateDate;

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



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPawnShopId() {
        return pawnShopId;
    }

    public void setPawnShopId(Integer pawnShopId) {
        this.pawnShopId = pawnShopId;
    }

    public Integer getPawnShopOwnerId() {
        return pawnShopOwnerId;
    }

    public void setPawnShopOwnerId(Integer pawnShopOwnerId) {
        this.pawnShopOwnerId = pawnShopOwnerId;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Date getExpectDueDate() {
        return expectDueDate;
    }

    public void setExpectDueDate(Date expectDueDate) {
        this.expectDueDate = expectDueDate;
    }

    public Date getActualDueDate() {
        return actualDueDate;
    }

    public void setActualDueDate(Date actualDueDate) {
        this.actualDueDate = actualDueDate;
    }

    public Double getExpectIncoming() {
        return expectIncoming;
    }

    public void setExpectIncoming(Double expectIncoming) {
        this.expectIncoming = expectIncoming;
    }

    public Double getActualIncoming() {
        return actualIncoming;
    }

    public void setActualIncoming(Double actualIncoming) {
        this.actualIncoming = actualIncoming;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPrePeriod() {
        return prePeriod;
    }

    public void setPrePeriod(Integer prePeriod) {
        this.prePeriod = prePeriod;
    }

    public Integer getPostPeriod() {
        return postPeriod;
    }

    public void setPostPeriod(Integer postPeriod) {
        this.postPeriod = postPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPlaceQuantity() {
        return placeQuantity;
    }

    public void setPlaceQuantity(int placeQuantity) {
        this.placeQuantity = placeQuantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(Double handlingFee) {
        this.handlingFee = handlingFee;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getHandlingFeeRate() {
        return handlingFeeRate;
    }

    public void setHandlingFeeRate(Double handlingFeeRate) {
        this.handlingFeeRate = handlingFeeRate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
