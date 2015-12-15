package com.pgt.cart.bean;

import java.util.Date;

public class CommerceItemBuilder {
	private int mId;
	private int mOrderId;
	private int mReferenceId;
	private String mName;
	private String mQuality;
	private double mListPrice;
	private double mSalePrice;
	private int mQuantity;
	private double mAmount;
	private int mSnapshotId;
	private int mIndex;
	private Date mCreationDate = new Date();
	private Date mUpdateDate;


	public CommerceItemBuilder setId(final int pId) {
		mId = pId;
		return this;
	}

	public CommerceItemBuilder setOrderId(final int pOrderId) {
		mOrderId = pOrderId;
		return this;
	}

	public CommerceItemBuilder setReferenceId(final int pReferenceId) {
		mReferenceId = pReferenceId;
		return this;
	}

	public CommerceItemBuilder setName(final String pName) {
		mName = pName;
		return this;
	}

	public CommerceItemBuilder setQuality(final String pQuality) {
		mQuality = pQuality;
		return this;
	}

	public CommerceItemBuilder setListPrice(final double pListPrice) {
		mListPrice = pListPrice;
		return this;
	}

	public CommerceItemBuilder setSalePrice(final double pSalePrice) {
		mSalePrice = pSalePrice;
		return this;
	}

	public CommerceItemBuilder setQuantity(final int pQuantity) {
		mQuantity = pQuantity;
		return this;
	}

	public CommerceItemBuilder setAmount(final double pAmount) {
		mAmount = pAmount;
		return this;
	}

	public CommerceItemBuilder setSnapshotId(final int pSnapshotId) {
		mSnapshotId = pSnapshotId;
		return this;
	}

	public CommerceItemBuilder setIndex(final int pIndex) {
		mIndex = pIndex;
		return this;
	}

	public CommerceItemBuilder setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
		return this;
	}

	public CommerceItemBuilder setUpdateDate(final Date pUpdateDate) {
		mUpdateDate = pUpdateDate;
		return this;
	}

	public CommerceItem createCommerceItem() {
		return new CommerceItem(mId, mOrderId, mReferenceId, mName, mQuality, mListPrice, mSalePrice, mQuantity, mAmount, mSnapshotId, mIndex, mCreationDate, mUpdateDate, null);
	}
}