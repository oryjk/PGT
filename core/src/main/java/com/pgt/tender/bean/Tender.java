package com.pgt.tender.bean;

import com.pgt.product.bean.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 1/16/16.
 */
public class Tender implements Serializable {
    private Integer tenderId;
    private Integer pawnshopId;
    private Integer pawnTicketId;
    private Double tenderTotal;
    private Integer tenderQuantity;
    private Date publishDate;
    private Date dueDate;
    private Double interestRate;
    private String name;
    private String description;
    private Integer prePeriod;
    private Integer postPeriod;
    private List<Product> products;
    private Date creationDate;
    private Date updateDate;

    public double getUnitPrice() {
        if (tenderQuantity > 0) {
            return tenderTotal / tenderQuantity;
        }
        return 0;
    }


    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getPawnshopId() {
        return pawnshopId;
    }

    public void setPawnshopId(Integer pawnshopId) {
        this.pawnshopId = pawnshopId;
    }

    public Integer getPawnTicketId() {
        return pawnTicketId;
    }

    public void setPawnTicketId(Integer pawnTicketId) {
        this.pawnTicketId = pawnTicketId;
    }

    public Double getTenderTotal() {
        return tenderTotal;
    }

    public void setTenderTotal(Double tenderTotal) {
        this.tenderTotal = tenderTotal;
    }

    public Integer getTenderQuantity() {
        return tenderQuantity;
    }

    public void setTenderQuantity(Integer tenderQuantity) {
        this.tenderQuantity = tenderQuantity;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
