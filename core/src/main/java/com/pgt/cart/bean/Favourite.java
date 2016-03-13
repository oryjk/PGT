package com.pgt.cart.bean;

import com.pgt.common.bean.Media;

import java.util.Date;

/**
 * Created by Yove on 11/12/2015.
 */
public class Favourite implements FavouriteType {

	private int mId;

	private int mUserId;

	private int mProductId;

	private String mName;

	private String mDescription;

	private double mFinalPrice;

	private int mSnapshotId;

	private Date mCreationDate;

	private int mProductStock;

	private Media mSnapshotMedia;

	private int mDiscussCount;

	private int mType;

	public Favourite() {
	}

	public Favourite(final int pId, final int pUserId, final int pProductId, final int pType, final String pName, final String pDescription, final double pFinalPrice, final int pSnapshotId) {
		mId = pId;
		mUserId = pUserId;
		mProductId = pProductId;
		mType = pType;
		mName = pName;
		mDescription = pDescription;
		mFinalPrice = pFinalPrice;
		mSnapshotId = pSnapshotId;
	}


	@Override
	public String toString() {
		return new StringBuilder().append("Favourite{").append("mId=").append(mId).append(", mUserId=").append(mUserId).append(", mProductId=").append(mProductId).append(", mName='").append(mName).append('\'').append(", mDescription='").append(mDescription).append('\'').append(", mFinalPrice=").append(mFinalPrice).append(", mSnapshotId=").append(mSnapshotId).append(", mCreationDate=").append(mCreationDate).append(", mProductStock=").append(mProductStock).append(", mSnapshotMedia=").append(mSnapshotMedia).append(", mDiscussCount=").append(mDiscussCount).append(", mType=").append(mType).append('}').toString();
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(final int pUserId) {
		mUserId = pUserId;
	}

	public int getProductId() {
		return mProductId;
	}

	public void setProductId(final int pProductId) {
		mProductId = pProductId;
	}

	public int getType() {
		return mType;
	}

	public void setType(final int pType) {
		mType = pType;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String pName) {
		mName = pName;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(final String pDescription) {
		mDescription = pDescription;
	}

	public double getFinalPrice() {
		return mFinalPrice;
	}

	public void setFinalPrice(final double pFinalPrice) {
		mFinalPrice = pFinalPrice;
	}

	public int getSnapshotId() {
		return mSnapshotId;
	}

	public void setSnapshotId(final int pSnapshotId) {
		mSnapshotId = pSnapshotId;
	}

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Media getSnapshotMedia() {
		return mSnapshotMedia;
	}

	public void setSnapshotMedia(final Media pSnapshotMedia) {
		mSnapshotMedia = pSnapshotMedia;
	}

	public int getDiscussCount() {
		return mDiscussCount;
	}

	public void setDiscussCount(final int pDiscussCount) {
		mDiscussCount = pDiscussCount;
	}

	public int getProductStock() {
		return mProductStock;
	}

	public void setProductStock(final int pProductStock) {
		mProductStock = pProductStock;
	}
}
