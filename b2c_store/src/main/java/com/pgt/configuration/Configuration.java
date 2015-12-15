package com.pgt.configuration;

/**
 * Created by carlwang on 11/19/15.
 */
public class Configuration {
	private Integer homeHotProductCount;
	private Boolean onlyShowInStock;
	private Integer productListPageCapacity;
	private String smsUrl = "http://www.tosms.cn/Api/SendSms.ashx";
	private String smsUsername = "dianjinzi";
	private String smsPassword = "E10ADC3949BA59ABBE56E057F20F883E";
	private String smsLoginContent = "点金子登录验证码是:";
	private String smsRegisterContent = "点金子注册验证码是:";
	private String smsResetPasswordContent = "点金子重设密码验证码是:";
	private Integer communicationCapacity;
	private Boolean useES = false;
	private Integer plpCapacity;
	private String resourcePath;

	public Integer getPlpCapacity() {
		return plpCapacity;
	}

	public void setPlpCapacity(Integer plpCapacity) {
		this.plpCapacity = plpCapacity;
	}

	private boolean smsMock = true;

	public Integer getCommunicationCapacity() {
		return communicationCapacity;
	}

	public void setCommunicationCapacity(Integer communicationCapacity) {
		this.communicationCapacity = communicationCapacity;
	}

	public String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsLoginContent() {
		return smsLoginContent;
	}

	public void setSmsLoginContent(String smsLoginContent) {
		this.smsLoginContent = smsLoginContent;
	}

	public String getSmsRegisterContent() {
		return smsRegisterContent;
	}

	public void setSmsRegisterContent(String smsRegisterContent) {
		this.smsRegisterContent = smsRegisterContent;
	}

	public Integer getHomeHotProductCount() {
		return homeHotProductCount;
	}

	public void setHomeHotProductCount(Integer homeHotProductCount) {
		this.homeHotProductCount = homeHotProductCount;
	}

	public Boolean getOnlyShowInStock() {
		return onlyShowInStock;
	}

	public void setOnlyShowInStock(Boolean onlyShowInStock) {
		this.onlyShowInStock = onlyShowInStock;
	}

	public Integer getProductListPageCapacity() {
		return productListPageCapacity;
	}

	public void setProductListPageCapacity(Integer productListPageCapacity) {
		this.productListPageCapacity = productListPageCapacity;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSmsResetPasswordContent() {
		return smsResetPasswordContent;
	}

	public void setSmsResetPasswordContent(String smsResetPasswordContent) {
		this.smsResetPasswordContent = smsResetPasswordContent;
	}

	public boolean isSmsMock() {
		return smsMock;
	}

	public void setSmsMock(boolean smsMock) {
		this.smsMock = smsMock;
	}

	public Boolean getUseES() {
		return useES;
	}

	public void setUseES(Boolean useES) {
		this.useES = useES;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
}