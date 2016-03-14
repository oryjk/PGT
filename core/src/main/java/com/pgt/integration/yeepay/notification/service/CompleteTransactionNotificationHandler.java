package com.pgt.integration.yeepay.notification.service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.bean.OrderType;
import com.pgt.cart.dao.P2PMapper;
import com.pgt.cart.dao.ShoppingCartDao;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.integration.yeepay.YeePayConfig;
import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayException;
import com.pgt.integration.yeepay.YeePayHelper;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.inventory.service.InventoryService;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.PaymentService;
import com.pgt.sms.service.SmsService;
import com.pgt.utils.Transactionable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

public class CompleteTransactionNotificationHandler extends Transactionable implements YeepayNotificationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompleteTransactionNotificationHandler.class);
	
	private DirectYeePay completeTransactionYeepay;
	
	private YeePayConfig config;
	
	private PaymentService paymentService;
	
	private ShoppingCartDao shoppingCartDao;

	private UserOrderDao userOrderDao;

	private InventoryService inventoryService;

	private DirectYeePay directTransactionYeepay;

	private P2PMapper p2pMapper;

	@Autowired
	private SmsService smsService;

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
			Order order = null;
			if (null == orderId) {
				LOGGER.warn("skip load order cause orderId is null");
			} else {
				order = getUserOrderDao().loadOrderHistory(orderId.intValue());
			}
			if (null == order) {
				LOGGER.warn("order is null, orderId:" + orderId);
			}
			if (null == order) {
				throw new IllegalArgumentException("order is null");
			}
			getInventoryService().lockInventory(order);
			// If not pass inventory check will occur exception, the following code will not execute.

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(YeePayConstants.PARAM_NAME_USER_ID, transactionLog.getUserId());
			params.put(YeePayConstants.PARAM_NAME_ORDER_ID, transactionLog.getOrderId());
			params.put(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID, transactionLog.getPaymentGroupId());
			params.put(YeePayConstants.PARAM_NAME_TRANSACTION_ID, transactionLog.getTransactionId());
			String trackingNo = YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId());
			params.put(YeePayConstants.PARAM_NAME_REQUEST_NO, trackingNo);
			params.put(YeePayConstants.PARAM_NAME_MODE, YeePayConstants.MODE_CONFIRM);
			params.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, getConfig().getCompleteTransactionNotifyUrl());
		
			Map<String, String> result = getCompleteTransactionYeepay().invoke(params);
			Transaction transaction = getPaymentService().findTransactionByTrackingNumber(trackingNo);
			Date now = new Date();

			handleResult(paymentGroup, trackingNo, result, transaction, now, order);
			if (YeePayConstants.CODE_SUCCESS.equals(result.get(YeePayConstants.PARAM_NAME_CODE)) &&
					order.getType() == OrderType.P2P_ORDER) {
				handleP2POrder(paymentGroup, result, now, order);
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			getPaymentService().setAsRollback();
			throw new YeePayException(e);
		}finally {
			getPaymentService().commit();
		}
	}

	private void handleP2POrder(PaymentGroup paymentGroup, Map<String, String> result, Date now, Order order) throws IOException {

		P2PInfo info = getP2pMapper().queryInfoByOrderId(order.getId());
		double resultAmount = order.getTotal() - info.getHandlingFee();
		// TODO ROUND;
		Map<String, Object> params = new HashMap<String, Object>();
		Transaction transaction = new Transaction();
		transaction.setOrderId(Long.valueOf(order.getUserId()));
		transaction.setAmount(resultAmount);
		transaction.setPaymentType(PaymentConstants.PAYMENT_TYPE_YEEPAY);
		transaction.setPaymentGroupId(Long.valueOf(PaymentConstants.PLATFORM_PAYMENT_GROUP_ID));
		getPaymentService().createTransaction(transaction);

		params.put(YeePayConstants.PARAM_NAME_USER_ID, Long.valueOf(order.getUserId()));
		params.put(YeePayConstants.PARAM_NAME_ORDER_ID, transaction.getOrderId());
//		params.put(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID, transactionLog.getPaymentGroupId());


		params.put(YeePayConstants.PARAM_NAME_TRANSACTION_ID, transaction.getId());
//		String trackingNo = YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId());
//		params.put(YeePayConstants.PARAM_NAME_REQUEST_NO, trackingNo);
		params.put(YeePayConstants.PARAM_NAME_MODE, YeePayConstants.MODE_CONFIRM);
		params.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, getConfig().getCompleteTransactionNotifyUrl());

		params.put(YeePayConstants.PARAM_NAME_USER_TYPE, YeePayConstants.USER_TYPE_MERCHANT);

		params.put(YeePayConstants.PARAM_NAME_PLATFORM_USER_NO, getConfig().getTargetPlatformUserNo());
		Map<String, Object> detailsMap = new HashMap<String, Object>();
		List<Map<String, Object>> detailsList = new ArrayList<>();
		params.put(YeePayConstants.PARAM_NAME_DETAILS, detailsMap);
		detailsMap.put(YeePayConstants.PARAM_NAME_DETAIL, detailsList);
		Map<String, Object> detailMap = new HashMap<String, Object>();
		detailsList.add(detailMap);

		Map<String, Object> detail = new HashMap<String, Object>();
		detail.put(YeePayConstants.PARAM_NAME_TARGET_USER_TYPE, YeePayConstants.USER_TYPE_MEMBER);
		// TODO
		String platformUserNo = YeePayHelper.generateOutboundRequestNo(getConfig(), Long.valueOf(info.getPawnShopOwnerId()));
		detail.put(YeePayConstants.PARAM_NAME_TARGET_PLATFORM_USER_NO, platformUserNo);
		detail.put(YeePayConstants.PARAM_NAME_AMOUNT, resultAmount);
		detailMap.put(YeePayConstants.PARAM_NAME_DETAIL, detail);
		Map<String, String> invokResult = getDirectTransactionYeepay().invoke(params);
		transaction.setTrackingNo((String)params.get(YeePayConstants.PARAM_NAME_REQUEST_NO));
		if (YeePayConstants.CODE_SUCCESS.equals(invokResult.get(YeePayConstants.PARAM_NAME_CODE))) {
			transaction.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
			order.setStatus(OrderStatus.PAID_TRANSFER_TO_OWNER);
		} else {
			transaction.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
		}
		getPaymentService().updateTransaction(transaction);
	}

	public void handleResult(PaymentGroup paymentGroup, String trackingNo, Map<String, String> result,
			Transaction transaction, Date now, Order order) {
		if (YeePayConstants.CODE_SUCCESS.equals(result.get(YeePayConstants.PARAM_NAME_CODE))) {
			// UPDATE ORDER STATUS
			if (null != order) {
				order.setStatus(OrderStatus.PAID);
				order.setUpdateDate(now);
				getShoppingCartDao().updateOrder(order);
				smsService.sendPaidOrderMessage(order);
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

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public DirectYeePay getDirectTransactionYeepay() {
		return directTransactionYeepay;
	}

	public void setDirectTransactionYeepay(DirectYeePay directTransactionYeepay) {
		this.directTransactionYeepay = directTransactionYeepay;
	}

	public P2PMapper getP2pMapper() {
		return p2pMapper;
	}

	public void setP2pMapper(P2PMapper p2pMapper) {
		this.p2pMapper = p2pMapper;
	}
}
