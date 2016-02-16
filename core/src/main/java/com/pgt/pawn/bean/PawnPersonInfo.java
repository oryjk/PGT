package com.pgt.pawn.bean;

import java.io.Serializable;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
public class PawnPersonInfo implements Serializable {

    private Integer id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String address;
    private String detailAddress;
    private String pawnType;
    private String smsCode;
    private String status;
    private String type;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPawnType() {
        return pawnType;
    }

    public void setPawnType(String pawnType) {
        this.pawnType = pawnType;
    }
}
