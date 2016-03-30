package com.pgt.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 11/19/15.
 */
public class Configuration {
	private Integer homeHotProductCount;
	private Boolean onlyShowInStock;
	private Integer productListPageCapacity;

	private String smsUrl                  = "http://www.tosms.cn/Api/SendSms.ashx";

	private String smsUsername             = "dianjinzi";

	private String smsPassword             = "E10ADC3949BA59ABBE56E057F20F883E";

	private String smsLoginContent         = "点金子登录验证码是:";

	private String smsRegisterContent      = "点金子注册验证码是:";
	private String smsResetPasswordContent = "点金子重设密码验证码是:";

	private String smsOnlinePawnContent = "点金子在线典当验证码是:";
	private Integer communicationCapacity;
	private Boolean useES = false;
	private Integer plpCapacity;
	private String resourcePath;
	private String initialDataPath;
	private String imagePath;
	private Integer adminPlpCapacity=5;
	private Integer adminCategoryCapacity;
	private String imageFolder="/image/upload/";
	private String staticServer="http://www.pgt_admin.com";
	private Site currentSite;
	public static final String ADMIN_PHONENUMBER = "";

	private boolean useProxy=true;

	private boolean smsMock = false;

	private Map pawnType;

	private String domain =".dev_dianjinzi.com";
	private String userCacheTokenKey="user_cache_token";

	public Map getPawnType() {
		return pawnType;
	}

	public void setPawnType(Map pawnType) {
		this.pawnType = pawnType;
	}

	private List<String> serviceTels=new ArrayList<>();
	private String smsOrderContent="收到成功支付订单，请尽快去管理系统查看，订单号为：";
	public Integer getPlpCapacity() {
		return plpCapacity;
	}

	public void setPlpCapacity(Integer plpCapacity) {
		this.plpCapacity = plpCapacity;
	}

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

	public String getInitialDataPath() {
		return initialDataPath;
	}

	public void setInitialDataPath(String initialDataPath) {
		this.initialDataPath = initialDataPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAdminPlpCapacity() {
		return adminPlpCapacity;
	}

	public void setAdminPlpCapacity(Integer adminPlpCapacity) {
		this.adminPlpCapacity = adminPlpCapacity;
	}

	public Integer getAdminCategoryCapacity() {
		return adminCategoryCapacity;
	}

	public void setAdminCategoryCapacity(Integer adminCategoryCapacity) {
		this.adminCategoryCapacity = adminCategoryCapacity;
	}

	public String getImageFolder() {
		return imageFolder;
	}

	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}

	public String getStaticServer() {
		return staticServer;
	}

	public void setStaticServer(String staticServer) {
		this.staticServer = staticServer;
	}

	public boolean getUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getSmsOnlinePawnContent () {
		return smsOnlinePawnContent;
	}

	public void setSmsOnlinePawnContent (String smsOnlinePawnContent) {
		this.smsOnlinePawnContent = smsOnlinePawnContent;
	}

	public List<String> getServiceTels() {
		return serviceTels;
	}

	public void setServiceTels(List<String> serviceTels) {
		this.serviceTels = serviceTels;
	}

	public String getSmsOrderContent() {
		return smsOrderContent;
	}

	public void setSmsOrderContent(String smsOrderContent) {
		this.smsOrderContent = smsOrderContent;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUserCacheTokenKey() {
		return userCacheTokenKey;
	}

	public void setUserCacheTokenKey(String userCacheTokenKey) {
		this.userCacheTokenKey = userCacheTokenKey;
	}

	public Site getCurrentSite() {
		return currentSite;
	}

	public void setCurrentSite(Site currentSite) {
		this.currentSite = currentSite;
	}
}
