package com.pgt.pawn.bean;

import com.pgt.pawn.validation.group.PawnGroup;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
public class PawnPersonInfo implements Serializable {

    private Integer id;
    @NotEmpty(message = "{NotEmpty.pawn.name}", groups = {PawnGroup.class})
    @Length(min = 2,max = 8,message = "{Length.pawn.name}", groups = {PawnGroup.class})
    private String name;
    @NotEmpty(message = "{NotEmpty.pawn.gender}")
    private String gender;
    @Pattern(regexp = "[0-9]{11}", message = "{Pattern.pawn.phoneNumber}", groups = {PawnGroup.class})
    @NotEmpty(message = "{NotNull.pawn.phoneNumber}", groups = {PawnGroup.class})
    private String phoneNumber;
    private String address;
    @NotEmpty(message = "{NotEmpty.address.area},",groups = {PawnGroup.class})
    private String detailAddress;
    @NotEmpty(message = "{NotEmpty.pawn.pawnType}",groups = {PawnGroup.class})
    private String pawnType;
    @NotEmpty(message = "{NotNull.pawn.smsCode}", groups = {PawnGroup.class})
    private String smsCode;
    private String status;
    private String type;
    private Date createDate;
    @NotNull(message = "{NotEmpty.pawn.price}",groups = {PawnGroup.class})
    private Double price;
    @NotEmpty(message = "{NotEmpty.address.area}",groups = {PawnGroup.class})
    private String	province;
    @NotEmpty(message = "{NotEmpty.address.area}",groups = {PawnGroup.class})
    private String	city;
    @NotEmpty(message = "{NotEmpty.address.area}",groups = {PawnGroup.class})
    private String	district;

    private String contacts;


    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


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
