package com.pgt.payment.service;

import java.util.Date;
import java.util.List;

import com.pgt.payment.bean.TransactionLogQueryBean;
import com.pgt.payment.bean.TransactionQueryBean;
import com.pgt.utils.PaginationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.cart.bean.Order;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.dao.PaymentMapper;
import com.pgt.utils.Transactionable;

@Service(value = "paymentService")
public class PaymentService extends Transactionable {

	@Autowired
	private PaymentMapper paymentMapper;

	public PaymentGroup findPaymentGroupByOrderId(int id) {
		return getPaymentMapper().findPaymentGroupByOrderId(id);
	}

	public boolean maintainPaymentGroup(PaymentGroup paymentGroup) {
		int orderId = paymentGroup.getOrderId().intValue();
		PaymentGroup existPaymentGroup = findPaymentGroupByOrderId(orderId);
		if (null != existPaymentGroup) {
			paymentGroup.setId(existPaymentGroup.getId());
			updatePaymentGroup(paymentGroup);
		} else {
			createPaymentGroup(paymentGroup);
		}
		return true;
	}

	public PaymentGroup maintainPaymentGroup(Order order, String paymentMethod) {
		PaymentGroup paymentGroup = new PaymentGroup();
		Date now = new Date();
		Long orderId = Long.valueOf(order.getId());
		paymentGroup.setOrderId(orderId);
		paymentGroup.setAmount(order.getTotal());
		paymentGroup.setCreateDate(now);
		paymentGroup.setUpdateDate(now);
		paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
		if (PaymentConstants.METHOD_ALIPAY.equals(paymentMethod)) {
			paymentGroup.setType(PaymentConstants.PAYMENT_TYPE_ALIPAY);
		} else if (PaymentConstants.METHOD_YEEPAY.equals(paymentMethod)) {
			paymentGroup.setType(PaymentConstants.PAYMENT_TYPE_YEEPAY);
		}
		maintainPaymentGroup(paymentGroup);
		return paymentGroup;
	}

	public void createPaymentGroup(PaymentGroup paymentGroup) {
		getPaymentMapper().createPaymentGroup(paymentGroup);
	}

	public void updatePaymentGroup(PaymentGroup paymentGroup) {
		getPaymentMapper().updatePaymentGroup(paymentGroup);
	}

	public void createTransaction(Transaction transaction) {
		getPaymentMapper().createTransaction(transaction);
	}

	public void updateTransaction(Transaction transaction) {
		getPaymentMapper().updateTransaction(transaction);
	}

	public List<Transaction> queryTransaction(Integer orderId, String paymentType, Integer state, String trackNo,
											  Date startTime, Date endTime, PaginationBean paginationBean) {
		TransactionQueryBean queryBean = new TransactionQueryBean();
		queryBean.setOrderId(orderId);
		queryBean.setPaymentType(paymentType);
		queryBean.setState(state);
		queryBean.setTrackNo(trackNo);
		queryBean.setStartTime(startTime);
		queryBean.setEndTime(endTime);
		queryBean.setPaginationBean(paginationBean);
		int totalAmount = getPaymentMapper().queryTransactionTotalAmount(queryBean);
		paginationBean.setTotalAmount(totalAmount);

		return getPaymentMapper().queryTransaction(queryBean);
	}


	public PaymentGroup findPaymentGroupById(Long paymentGroupId) {
		int id = paymentGroupId.intValue();
		return getPaymentMapper().findPaymentGroupById(id);
	}

	public Transaction findTransactionByTrackingNumber(String trackingNo) {
		return getPaymentMapper().findTransactionByTrackingNumber(trackingNo);
	}

	public PaymentMapper getPaymentMapper() {
		return paymentMapper;
	}

	public void setPaymentMapper(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper;
	}

}
