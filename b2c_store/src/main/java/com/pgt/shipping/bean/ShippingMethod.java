package com.pgt.shipping.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class ShippingMethod implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer	id;
	@NotEmpty(message = "{NotEmpty.address.name}")
	private String	name;
	@NotEmpty(message = "{NotEmpty.address.phone}")
	@Pattern(regexp = "[0-9]{11}", message = "{Pattern.address.phone}")
	private String	phone;
	@NotNull(message = "{NotEmpty.pickup.location}")
	private Integer	locationId;
	private Integer	status;
	private Date	creationDate;
	private Date	updateDate;

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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("ShippingMethod[").append("id=").append(id).append(",").append("name=").append(name)
				.append(",").append("status=").append(status).append(",").append("creationDate=").append(creationDate)
				.append(",").append("updateDate=").append(updateDate).append("]").toString();
	}

}
