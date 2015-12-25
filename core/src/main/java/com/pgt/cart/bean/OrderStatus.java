package com.pgt.cart.bean;

/**
 * Created by Yove on 10/27/2015.
 */
public interface OrderStatus {

	int	INITIAL 			= 10;
	int	FILLED_SHIPPING 	= 20;
	int START_PAY           = 25;
	int	PAID  				= 30;
	int	NO_PENDING_ACTION	= 100;
	int	CANCEL 				= -10;

}
