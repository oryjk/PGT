package com.pgt.cart.service;

import com.pgt.cart.bean.BrowsedType;
import com.pgt.cart.bean.OrderType;
import org.springframework.stereotype.Service;

/**
 * Created by Yove on 1/27/2016.
 */
@Service("shoppingCartConfiguration")
public class ShoppingCartConfiguration {

	private int mMaxItemCount4Cart = 3;

	private int mDefaultOrderType = OrderType.B2C_ORDER;

	private int mDefaultBrowsedType = BrowsedType.B2C_PRODUCT;

	private int mBrowsedProductCount = 20;

	private int mBrowsedCookieExpired = 604800; //60s * 60m * 24h * 7d

	public ShoppingCartConfiguration() {
	}

	public int getMaxItemCount4Cart() {
		return mMaxItemCount4Cart;
	}

	public void setMaxItemCount4Cart(final int pMaxItemCount4Cart) {
		mMaxItemCount4Cart = pMaxItemCount4Cart;
	}

	public int getDefaultOrderType() {
		return mDefaultOrderType;
	}

	public void setDefaultOrderType(final int pDefaultOrderType) {
		mDefaultOrderType = pDefaultOrderType;
	}

	public int getDefaultBrowsedType() {
		return mDefaultBrowsedType;
	}

	public void setDefaultBrowsedType(final int pDefaultBrowsedType) {
		mDefaultBrowsedType = pDefaultBrowsedType;
	}

	public int getBrowsedProductCount() {
		return mBrowsedProductCount;
	}

	public void setBrowsedProductCount(final int pBrowsedProductCount) {
		mBrowsedProductCount = pBrowsedProductCount;
	}

	public int getBrowsedCookieExpired() {
		return mBrowsedCookieExpired;
	}

	public void setBrowsedCookieExpired(final int pBrowsedCookieExpired) {
		mBrowsedCookieExpired = pBrowsedCookieExpired;
	}
}