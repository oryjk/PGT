package com.pgt.cart.service;

import com.pgt.cart.bean.OrderType;
import org.springframework.stereotype.Service;

/**
 * Created by Yove on 1/27/2016.
 */
@Service(value = "shoppingCartConfiguration")
public class ShoppingCartConfiguration {

	private int mMaxItemCount4Cart = 2;

	private int mDefaultOrderType = OrderType.B2C_ORDER;

	public int getMaxItemCount4Cart () {
		return mMaxItemCount4Cart;
	}

	public void setMaxItemCount4Cart (final int pMaxItemCount4Cart) {
		mMaxItemCount4Cart = pMaxItemCount4Cart;
	}

	public int getDefaultOrderType () {
		return mDefaultOrderType;
	}

	public void setDefaultOrderType (final int pDefaultOrderType) {
		mDefaultOrderType = pDefaultOrderType;
	}
}
