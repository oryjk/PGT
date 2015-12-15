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
	private Product mProduct;
	private Date mCreationDate;
	private Date mUpdateDate;

	public BrowsedProductVO() {
	}

	public BrowsedProductVO(final int pUserId, final int pProductId) {
		mUserId = pUserId;
		mProductId = pProductId;
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
