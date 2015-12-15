package com.pgt.payment.dao;

import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;

@Component
public interface PaymentMapper extends SqlMapper {

	PaymentGroup findPaymentGroupByOrderId(int id);

	void createPaymentGroup(PaymentGroup paymentGroup);

	void updatePaymentGroup(PaymentGroup paymentGroup);

	void createTransaction(Transaction transaction);

	void updateTransaction(Transaction transaction);

	PaymentGroup findPaymentGroupById(int id);

	Transaction findTransactionByTrackingNumber(String trackingNo);

}
