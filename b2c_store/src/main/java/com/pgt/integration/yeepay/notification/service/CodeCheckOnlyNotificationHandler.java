package com.pgt.integration.yeepay.notification.service;

import java.util.Map;

import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayException;
import com.pgt.payment.bean.TransactionLog;

public class CodeCheckOnlyNotificationHandler implements YeepayNotificationHandler {

	@Override
	public void handleCallback(Map<String, String> inboundParams, TransactionLog transactionLog)
			throws YeePayException {
		String code = inboundParams.get(YeePayConstants.PARAM_NAME_CODE);
		if (YeePayConstants.CODE_SUCCESS.equals(code)) {
			return;
		}

		// TODO THROW EXCEPTION
	}

	@Override
	public void handleNotify(Map<String, String> inboundParams, TransactionLog transactionLog) throws YeePayException {
		handleCallback(inboundParams, transactionLog);

	}

}
