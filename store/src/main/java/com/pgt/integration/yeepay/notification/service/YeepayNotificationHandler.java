package com.pgt.integration.yeepay.notification.service;

import java.util.Map;

import com.pgt.integration.yeepay.YeePayException;
import com.pgt.payment.bean.TransactionLog;

public interface YeepayNotificationHandler {

	void handleCallback(Map<String, String> inboundParams, TransactionLog transactionLog) throws YeePayException;

	void handleNotify(Map<String, String> inboundParams, TransactionLog transactionLog) throws YeePayException;
}
