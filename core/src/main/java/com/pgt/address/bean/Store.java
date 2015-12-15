package com.pgt.address.bean;

import java.io.Serializable;

public class Store implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Integer				id;
	private String				address;
	private String				phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
