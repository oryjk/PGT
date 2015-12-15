package com.pgt.cart.bean;

import com.pgt.common.bean.Media;

import java.util.Date;

/**
 * Created by Yove on 10/26/2015.
 */
public class CommerceItem implements Comparable<CommerceItem> {

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

	private Media mSnapshotMedia;

	@Override
	public int compareTo(final CommerceItem o) {
		if (o == null) {
			return -1;
		} else {
			return this.getCreationDate().compareTo(o.getCreationDate());
		}
	}

	public CommerceItem() {
	}

	public CommerceItem(final int pId, final int pOrderId, final int pReferenceId, final String pName, final String pQuality, final double pListPrice, final double pSalePrice, final int pQuantity, final double pAmount, final int pSnapshotId, final int pIndex, final Date pCreationDate, final Date pUpdateDate, final Media pSnapshotMedia) {
		mId = pId;
		mOrderId = pOrderId;
		mReferenceId = pReferenceId;
		mName = pName;
		mQuality = pQuality;
		mListPrice = pListPrice;
		mSalePrice = pSalePrice;
		mQuantity = pQuantity;
		mAmount = pAmount;
		mSnapshotId = pSnapshotId;
		mIndex = pIndex;
		mCreationDate = pCreationDate;
		mUpdateDate = pUpdateDate;
		mSnapshotMedia = pSnapshotMedia;
	}

	@Override
	public String toString() {
		return "CommerceItem{" +
				"mId=" + mId +
				", mOrderId=" + mOrderId +
				", mReferenceId=" + mReferenceId +
				", mName='" + mName + '\'' +
				", mQuality='" + mQuality + '\'' +
				", mListPrice=" + mListPrice +
				", mSalePrice=" + mSalePrice +
				", mQuantity=" + mQuantity +
				", mAmount=" + mAmount +
				", mSnapshotId=" + mSnapshotId +
				", mIndex=" + mIndex +
				", mCreationDate=" + mCreationDate +
				", mUpdateDate=" + mUpdateDate +
				", mSnapshotMedia=" + mSnapshotMedia +
				'}';
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public int getOrderId() {
		return mOrderId;
	}

	public void setOrderId(final int pOrderId) {
		mOrderId = pOrderId;
	}

	public int getReferenceId() {
		return mReferenceId;
	}

	public void setReferenceId(final int pReferenceId) {
		mReferenceId = pReferenceId;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String pName) {
		mName = pName;
	}

	public String getQuality() {
		return mQuality;
	}

	public void setQuality(final String pQuality) {
		mQuality = pQuality;
	}

	public double getListPrice() {
		return mListPrice;
	}

	public void setListPrice(final double pListPrice) {
		mListPrice = pListPrice;
	}

	public double getSalePrice() {
		return mSalePrice;
	}

	public void setSalePrice(final double pSalePrice) {
		mSalePrice = pSalePrice;
	}

	public int getQuantity() {
		return mQuantity;
	}

	public void setQuantity(final int pQuantity) {
		mQuantity = pQuantity;
	}

	public double getAmount() {
		return mAmount;
	}

	public void setAmount(final double pAmount) {
		mAmount = pAmount;
	}

	public int getSnapshotId() {
		return mSnapshotId;
	}

	public void setSnapshotId(final int pSnapshotId) {
		mSnapshotId = pSnapshotId;
	}

	public int getIndex() {
		return mIndex;
	}

	public void setIndex(final int pIndex) {
		mIndex = pIndex;
	}

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Date getUpdateDate() {
		return mUpdateDate;
	}

	public void setUpdateDate(final Date pUpdateDate) {
		mUpdateDate = pUpdateDate;
	}

	public Media getSnapshotMedia() {
		return mSnapshotMedia;
	}

	public void setSnapshotMedia(final Media pSnapshotMedia) {
		mSnapshotMedia = pSnapshotMedia;
	}
}
