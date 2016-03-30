package com.pgt.pawn.bean;

import java.io.Serializable;

/**
 * Created by carlwang on 1/28/16.
 */
public class OnlinePawn implements Serializable {
	private Integer id;

	private Integer categoryId;

	private Integer durationDate;

	private Double pawnTotal;

	private String phoneNumber;

	public Integer getId () {
		return id;
	}

	public void setId (Integer id) {
		this.id = id;
	}

	public Integer getCategoryId () {
		return categoryId;
	}

	public void setCategoryId (Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getDurationDate () {
		return durationDate;
	}

	public void setDurationDate (Integer durationDate) {
		this.durationDate = durationDate;
	}

	public Double getPawnTotal () {
		return pawnTotal;
	}

	public void setPawnTotal (Double pawnTotal) {
		this.pawnTotal = pawnTotal;
	}

	public String getPhoneNumber () {
		return phoneNumber;
	}

	public void setPhoneNumber (String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
