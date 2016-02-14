package com.pgt.pawn.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 1/16/16.
 */
public class Pawnshop implements Serializable {

	private Integer mPawnshopId;
	private String mName;
	private Integer mOwnerId;
	private Integer mManagerId;
	private String mAddress;
	private Date mCreationDate;
	private Date mUpdateDate;

	public Integer getPawnshopId() {
		return mPawnshopId;
	}

	public void setPawnshopId(Integer pawnshopId) {
		this.mPawnshopId = pawnshopId;
	}

	public Integer getOwnerId() {
		return mOwnerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.mOwnerId = ownerId;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		this.mAddress = address;
	}

	public Integer getManagerId() {
		return mManagerId;
	}

	public void setManagerId(Integer managerId) {
		this.mManagerId = managerId;
	}

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.mCreationDate = creationDate;
	}

	public Date getUpdateDate() {
		return mUpdateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.mUpdateDate = updateDate;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}
}
