package com.pgt.integration.yeepay.notification.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pgt.cart.bean.Order;
import com.pgt.cart.dao.ShoppingCartDao;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.integration.yeepay.YeePayConfig;
import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayException;
import com.pgt.integration.yeepay.YeePayHelper;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.PaymentService;
import com.pgt.utils.Transactionable;

public class CompleteTransactionNotificationHandler extends Transactionable implements YeepayNotificationHandler {

	private DirectYeePay completeTransactionYeepay;
	
	private YeePayConfig config;
	
	private PaymentService paymentService;
	
	private ShoppingCartDao shoppingCartDao;
	
	private UserOrderDao userOrderDao;
	
	@Override
	public void handleCallback(Map<String, String> inboundParams, TransactionLog transactionLog)
			throws YeePayException {
		
		Long paymentGroupId = transactionLog.getPaymentGroupId();
		Long orderId = transactionLog.getOrderId();
		
		getPaymentService().ensureTransaction();
		
		try {
			PaymentGroup paymentGroup = getPaymentService().findPaymentGroupById(paymentGroupId);
			if (null != paymentGroup && PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
				return;
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(YeePayConstants.PARAM_NAME_USER_ID, transactionLog.getUserId());
			params.put(YeePayConstants.PARAM_NAME_ORDER_ID, transactionLog.getOrderId());
			params.put(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID, transactionLog.getPaymentGroupId());
			params.put(YeePayConstants.PARAM_NAME_TRANSACTION_ID, transactionLog.getTransactionId());
			String trackingNo = YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId());
			params.put(YeePayConstants.PARAM_NAME_REQUEST_NO, trackingNo);
			params.put(YeePayConstants.PARAM_NAME_MODE, YeePayConstants.MODE_CONFIRM);
			params.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, getConfig().getCompleteTransactionNotifyUrl());
		
			Map<String, String> result = getCompleteTransactionYeepay().invok(params);
			Transaction transaction = getPaymentService().findTransactionByTrackingNumber(trackingNo);
			Date now = new Date();
			
			Order order = null;
			if (null == orderId) {
				// TODO LOG
			} else {
				order = getUserOrderDao().loadOrderHistory(orderId.intValue());
			}
			if (null == order) {
				// TODO LOG
			}
			
			handleResult(paymentGroup, trackingNo, result, transaction, now, order);
		} catch (IOException e) {
			getPaymentService().setAsRollback();
			throw new YeePayException(e);
		} finally {
			getPaymentService().commit();
		}
	}

	public void handleResult(PaymentGroup paymentGroup, String trackingNo, Map<String, String> result,
			Transaction transaction, Date now, Order order) {
		if (YeePayConstants.CODE_SUCCESS.equals(result.get(YeePayConstants.PARAM_NAME_CODE))) {
			// UPDATE ORDER STATUS
			if (null != order) {
				order.setStatus(3);
				order.setUpdateDate(now);
				getShoppingCartDao().updateOrder(order);
			}
			
			// update payment group status
			paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
			paymentGroup.setTrackingNo(trackingNo);
			getPaymentService().updatePaymentGroup(paymentGroup);
			
			// update transaction status
			transaction = getPaymentService().findTransactionByTrackingNumber(trackingNo);
			transaction.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
			transaction.setTrackingNo(trackingNo);
			transaction.setTransactionTime(now);
			transaction.setUpdateDate(now);
			getPaymentService().updateTransaction(transaction);
		} else {
			
			// update payment group status
			paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
			getPaymentService().updatePaymentGroup(paymentGroup);
			
			// update transaction status
			transaction.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
			transaction.setUpdateDate(now);
			getPaymentService().updateTransaction(transaction);
		}
	}

	@Override
	public void handleNotify(Map<String, String> inboundParams, TransactionLog transactionLog) throws YeePayException {
		handleCallback(inboundParams, transactionLog);
	}

	public DirectYeePay getCompleteTransactionYeepay() {
		return completeTransactionYeepay;
	}

	public void setCompleteTransactionYeepay(DirectYeePay completeTransactionYeepay) {
		this.completeTransactionYeepay = completeTransactionYeepay;
	}

	public YeePayConfig getConfig() {
		return config;
	}

	public void setConfig(YeePayConfig config) {
		this.config = config;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}

	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}

	public UserOrderDao getUserOrderDao() {
		return userOrderDao;
	}

	public void setUserOrderDao(UserOrderDao userOrderDao) {
		this.userOrderDao = userOrderDao;
	}

}
