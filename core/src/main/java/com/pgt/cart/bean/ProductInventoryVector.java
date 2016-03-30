package com.pgt.cart.bean;

/**
 * Created by Yove on 1/6/2016.
 */
public class ProductInventoryVector {

	private int mProductId;
	private int mStock;

	@Override
	public String toString() {
		return "ProductInventoryVector{" +
				"mProductId=" + mProductId +
				", mStock=" + mStock +
				'}';
	}

	public int getProductId() {
		return mProductId;
	}

	public void setProductId(final int pProductId) {
		mProductId = pProductId;
	}

	public int getStock() {
		return mStock;
	}

	public void setStock(final int pStock) {
		mStock = pStock;
	}
}
