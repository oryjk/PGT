package com.pgt.cart.bean;

/**
 * Created by Yove on 11/4/2015.
 */
public class ProductPriceVector {

	private int mProductId;
	private double mListPrice;
	private double mSalePrice;

	public double getFinalPrice() {
		if (mSalePrice > 0.0d) {
			return mSalePrice;
		}
		return mListPrice;
	}

	public boolean isListPriceValid() {
		return mListPrice > 0.0d;
	}

	public boolean isSalePriceValid() {
		return mSalePrice > 0.0d;
	}

	public int getProductId() {
		return mProductId;
	}

	public void setProductId(final int pProductId) {
		mProductId = pProductId;
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
}
