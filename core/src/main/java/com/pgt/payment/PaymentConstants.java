package com.pgt.payment;

public class PaymentConstants {

	public static final int PLATFORM_PAYMENT_GROUP_ID = -333;
	public static final int PAYMENT_STATUS_FAILED = -1;
	public static final int PAYMENT_STATUS_PROCCESSING = 0;
	public static final int PAYMENT_STATUS_SUCCESS = 1;
	
	public static final String METHOD_YEEPAY = "yeepay";
	public static final String METHOD_ALIPAY = "alipay";
	public static final String METHOD_WECHAT = "wechat";
	
	public static final int PAYMENT_TYPE_YEEPAY = 1;
	public static final int PAYMENT_TYPE_ALIPAY = 2;
	public static final int PAYMENT_TYPE_WECHAT = 3;
	
	public static final String PAID_SUCCESS_FLAG = "paidSuccessFlag";
}
