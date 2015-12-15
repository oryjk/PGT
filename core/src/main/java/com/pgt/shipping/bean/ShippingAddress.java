package com.pgt.shipping.bean;

import java.io.Serializable;

/**
 * 
 * @author ethanli
 *
 */
public class ShippingAddress implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private Integer				id;
	private String				name;
	private String				phone;
	private String				telephone;
	private String				province;
	private String				city;
	private String				district;
	private String				address;
	private String				email;
	private Integer				status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("ShippingAddress[").append("id=").append(id).append(",").append("name=").append(name)
				.append(",").append("phone=").append(phone).append(",").append("province=").append(province).append(",")
				.append("city=").append(city).append(",").append("district=").append(district).append(",")
				.append("address=").append(address).append(",").append("status=").append(status).append("]").toString();
	}

}
