package com.pgt.cart.bean;

import com.pgt.product.bean.Product;

import java.util.Date;

/**
 * Created by Yove on 11/21/2015.
 */
public class BrowsedProductVO {

	private int mId;

	private int mUserId;

	private int mProductId;

	private int mType;

	private Product mProduct;

	private Date mCreationDate;

	private Date mUpdateDate;

	public BrowsedProductVO() {
	}

	public BrowsedProductVO(final int pUserId, final int pProductId, final int pBrowsedType) {
		mUserId = pUserId;
		mProductId = pProductId;
		mType = pBrowsedType;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("BrowsedProductVO{").append("mId=").append(mId).append(", mUserId=").append(mUserId).append(", mProductId=").append(mProductId).append(", mType=").append(mType).append(", mProduct=").append(mProduct).append(", mCreationDate=").append(mCreationDate).append(", mUpdateDate=").append(mUpdateDate).append('}').toString();
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

	public Product getProduct() {
		return mProduct;
	}

	public void setProduct(final Product pProduct) {
		mProduct = pProduct;
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
}
