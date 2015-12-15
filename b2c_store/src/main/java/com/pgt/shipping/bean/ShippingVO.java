package com.pgt.shipping.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ethanli
 */
public class ShippingVO implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Integer				id;
	private String				orderId;
	private ShippingAddress		shippingAddress;
	private ShippingMethod		shippingMethod;
	private String				trackingNumber;
	private String				shippingType;
	private String				status;
	private Date				creationDate		= new Date();
	private Date				updateDate;
	private Double				amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("ShippingVO[").append("id=").append(id).append(",").append("orderId=").append(orderId)
				.append(",").append("status=").append(status).append(",").append("creationDate=").append(creationDate)
				.append(",").append("updateDate=").append(updateDate).append(",").append("amount=").append(amount)
				.append("]").toString();
	}
}
