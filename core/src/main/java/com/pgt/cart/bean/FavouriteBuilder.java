package com.pgt.cart.bean;

public class FavouriteBuilder implements FavouriteType {

	private int mId;

	private int mUserId;

	private int mProductId;

	private int mType;

	private String mName;

	private String mDescription;

	private double mFinalPrice;

	private int mSnapshotId;

	public FavouriteBuilder setId(final int pId) {
		mId = pId;
		return this;
	}

	public FavouriteBuilder setUserId(final int pUserId) {
		mUserId = pUserId;
		return this;
	}

	public FavouriteBuilder setProductId(final int pProductId) {
		mProductId = pProductId;
		return this;
	}

	public FavouriteBuilder setName(final String pName) {
		mName = pName;
		return this;
	}

	public FavouriteBuilder setDescription(final String pDescription) {
		mDescription = pDescription;
		return this;
	}

	public FavouriteBuilder setFinalPrice(final double pFinalPrice) {
		mFinalPrice = pFinalPrice;
		return this;
	}

	public FavouriteBuilder setSnapshotId(final int pSnapshotId) {
		mSnapshotId = pSnapshotId;
		return this;
	}

	public FavouriteBuilder setType(final int pType) {
		mType = pType;
		return this;
	}

	public Favourite createFavourite() {
		return new Favourite(mId, mUserId, mProductId, mType, mName, mDescription, mFinalPrice, mSnapshotId);
	}
}