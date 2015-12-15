package com.pgt.cart.bean;

/**
 * Created by Yove on 10/27/2015.
 */
public interface OrderStatus {

	int	INITIAL				= 1;
	int	COMPLETE			= 2;
	int	PAID 				= 3;
	int	SHIPPED				= 4;
	int	NO_PENDING_ACTION	= 5;

}
