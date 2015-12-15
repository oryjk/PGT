package com.pgt.integration.alipay;

public class AlipayConfig {
	private String	partner;
	private String	sellerEmail;
	private String	key;
	private String	service			= "create_direct_pay_by_user";
	private String	inputCharset	= "utf-8";
	private String	signTtype		= "MD5";
	private String	paymentType		= "1";
	private String	returnUrl;
	private String	notifyUrl;
	private String	alipayUrl;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignTtype() {
		return signTtype;
	}

	public void setSignTtype(String signTtype) {
		this.signTtype = signTtype;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAlipayUrl() {
		return alipayUrl;
	}

	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

}
