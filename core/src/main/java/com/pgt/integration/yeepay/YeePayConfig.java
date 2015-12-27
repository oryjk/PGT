
package com.pgt.integration.yeepay;

import java.util.Map;

import com.pgt.integration.yeepay.notification.service.YeepayNotificationHandler;

public class YeePayConfig {

	private String platformUserNoPrefix;

	private String platformNo;

	private String pfxPath;

	private String password;

	private int connectionTimeout;

	private int socketTimeout;

	private String directRequestUrl;

	private String callbackUrl;

	private String notifyUrl;

	private Map<String, Map<String, String>> serviceJspPath;

	private Map<String, Map<String, String>> requestParamConfig;

	private Map<String, String> requestUrl;

	private Map<String, YeepayNotificationHandler> notificationHandler;

	private Map<String, String> feeMode;

	private String withdrawType;

	private String targetPlatformUserNo;
	
	private String requestNoPrefix;
	
	private String completeTransactionNotifyUrl;

	private int transactionExpiredMin;

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getPfxPath() {
		return pfxPath;
	}

	public void setPfxPath(String pfxPath) {
		this.pfxPath = pfxPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public String getDirectRequestUrl() {
		return directRequestUrl;
	}

	public void setDirectRequestUrl(String directRequestUrl) {
		this.directRequestUrl = directRequestUrl;
	}

	public Map<String, Map<String, String>> getRequestParamConfig() {
		return requestParamConfig;
	}

	public void setRequestParamConfig(Map<String, Map<String, String>> requestParamConfig) {
		this.requestParamConfig = requestParamConfig;
	}

	public Map<String, String> getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(Map<String, String> requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Map<String, YeepayNotificationHandler> getNotificationHandler() {
		return notificationHandler;
	}

	public void setNotificationHandler(Map<String, YeepayNotificationHandler> notificationHandler) {
		this.notificationHandler = notificationHandler;
	}

	public String getPlatformUserNoPrefix() {
		return platformUserNoPrefix;
	}

	public void setPlatformUserNoPrefix(String platformUserNoPrefix) {
		this.platformUserNoPrefix = platformUserNoPrefix;
	}

	public Map<String, String> getFeeMode() {
		return feeMode;
	}

	public void setFeeMode(Map<String, String> feeMode) {
		this.feeMode = feeMode;
	}

	public Map<String, Map<String, String>> getServiceJspPath() {
		return serviceJspPath;
	}

	public void setServiceJspPath(Map<String, Map<String, String>> serviceJspPath) {
		this.serviceJspPath = serviceJspPath;
	}

	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}

	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}

	public String getRequestNoPrefix() {
		return requestNoPrefix;
	}

	public void setRequestNoPrefix(String requestNoPrefix) {
		this.requestNoPrefix = requestNoPrefix;
	}

	public String getCompleteTransactionNotifyUrl() {
		return completeTransactionNotifyUrl;
	}

	public void setCompleteTransactionNotifyUrl(String completeTransactionNotifyUrl) {
		this.completeTransactionNotifyUrl = completeTransactionNotifyUrl;
	}

	public int getTransactionExpiredMin() {
		return transactionExpiredMin;
	}

	public void setTransactionExpiredMin(int transactionExpiredMin) {
		this.transactionExpiredMin = transactionExpiredMin;
	}
}
