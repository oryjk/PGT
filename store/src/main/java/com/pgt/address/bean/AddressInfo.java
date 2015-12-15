package com.pgt.address.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer	id;
	private Integer	userId;

	@NotEmpty(message = "{NotEmpty.address.name}")
	private String name;

	@NotEmpty(message = "{NotEmpty.address.phone}")
	@Pattern(regexp = "[0-9]{11}", message = "{Pattern.address.phone}")
	private String	phone;
	private String	telephone;

	@NotEmpty(message = "{NotEmpty.address.area}")
	private String	province;
	@NotEmpty(message = "{NotEmpty.address.area}")
	private String	city;
	@NotEmpty(message = "{NotEmpty.address.area}")
	private String	district;
	@NotEmpty(message = "{NotEmpty.address.address}")
	private String	address;

//	@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", message = "{Pattern.address.email}")
	private String	email;
	private Integer	status;
	private Boolean	primary	= false;
	private Date	creationDate;
	private Date	updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean isPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
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
