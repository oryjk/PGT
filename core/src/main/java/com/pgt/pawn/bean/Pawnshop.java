package com.pgt.pawn.bean;

import com.pgt.base.bean.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 1/16/16.
 */
public class Pawnshop implements Serializable{
    private Integer pawnshopId;
    private Integer ownerId;
    private String address;
    private Integer managerId;
    private Date creationDate;
    private Date updateDate;
    private String name;

    public Integer getPawnshopId() {
        return pawnshopId;
    }

    public void setPawnshopId(Integer pawnshopId) {
        this.pawnshopId = pawnshopId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
