package com.pgt.integration.yeepay.direct.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pgt.integration.yeepay.ParamValidateor;
import com.pgt.integration.yeepay.YeePayConfig;
import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayHelper;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.TransactionLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectYeePay {

	private YeePayConfig config;
	private String serviceName;
	private TransactionLogService transactionLogService;

	private ParamValidateor paramValidateor;
	
	private boolean requireRequestNo;

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectYeePay.class);

	public Map<String, String> invoke(Map<String, Object> params) throws IOException {

		Map<String, Object> yeepayParams = new HashMap<>();
		yeepayParams.putAll(params);
		yeepayParams.remove(YeePayConstants.PARAM_NAME_USER_ID);
		yeepayParams.remove(YeePayConstants.PARAM_NAME_ORDER_ID);
		yeepayParams.remove(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID);
		yeepayParams.remove(YeePayConstants.PARAM_NAME_TRANSACTION_ID);
		yeepayParams.put(YeePayConstants.PARAM_NAME_PLATFORM_NO, getConfig().getPlatformNo());
		getParamValidateor().validInputParam(getConfig(), yeepayParams);

		Long userId = (Long) params.get(YeePayConstants.PARAM_NAME_USER_ID);
		Long orderId = (Long) params.get(YeePayConstants.PARAM_NAME_ORDER_ID);
		Long paymentGroupId = (Long) params.get(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID);
		Long transactionId = (Long) params.get(YeePayConstants.PARAM_NAME_TRANSACTION_ID);

		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setUserId(userId);
		transactionLog.setOrderId(orderId);
		transactionLog.setPaymentGroupId(paymentGroupId);
		transactionLog.setTransactionId(transactionId);
		transactionLog.setPaymentType(YeePayConstants.PAYMENT_TYPE);
		transactionLog.setServiceName(getServiceName());
		getTransactionLogService().createTransactionLog(transactionLog);

		if (isRequireRequestNo()) {
			if (null == yeepayParams.get(YeePayConstants.PARAM_NAME_REQUEST_NO)) {
				yeepayParams.put(YeePayConstants.PARAM_NAME_REQUEST_NO, YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId()));	
			}
		}

		String requestXML = YeePayHelper.generateRequestXml(getConfig(), yeepayParams);
		String requestSign = YeePayHelper.generateSign(getConfig(), requestXML);
		StringBuilder outBoundBuilder = new StringBuilder();
		outBoundBuilder.append("serviceName:\n");
		outBoundBuilder.append(getServiceName());
		outBoundBuilder.append("\n");
		outBoundBuilder.append("req: \n");
		outBoundBuilder.append(requestXML);
		outBoundBuilder.append("\n");
		outBoundBuilder.append("sign: \n");
		outBoundBuilder.append(requestSign);
		transactionLog.setOutbound(outBoundBuilder.toString());
		transactionLog.setOutboundTime(new Date());
		LOGGER.info(outBoundBuilder.toString());
		getTransactionLogService().updateTransactionLog(transactionLog);
		String response = YeePayHelper.directCall(getConfig(), getServiceName(), requestXML, requestSign);
		transactionLog.setInbound(response);
		transactionLog.setInboundTime(new Date());
		getTransactionLogService().updateTransactionLog(transactionLog);
		Map<String, String> result = YeePayHelper.parseXml(response);
		return result;
	}

	public YeePayConfig getConfig() {
		return config;
	}

	public void setConfig(YeePayConfig config) {
		this.config = config;
	}

	public TransactionLogService getTransactionLogService() {
		return transactionLogService;
	}

	public void setTransactionLogService(TransactionLogService transactionLogService) {
		this.transactionLogService = transactionLogService;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ParamValidateor getParamValidateor() {
		return paramValidateor;
	}

	public void setParamValidateor(ParamValidateor paramValidateor) {
		this.paramValidateor = paramValidateor;
	}

	public boolean isRequireRequestNo() {
		return requireRequestNo;
	}

	public void setRequireRequestNo(boolean requireRequestNo) {
		this.requireRequestNo = requireRequestNo;
	}

}
