package com.pgt.payment.dao;

import com.pgt.payment.bean.TransactionQueryBean;
import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;

import java.util.List;

@Component
public interface PaymentMapper extends SqlMapper {

	PaymentGroup findPaymentGroupByOrderId(int id);

	void createPaymentGroup(PaymentGroup paymentGroup);

	void updatePaymentGroup(PaymentGroup paymentGroup);

	void createTransaction(Transaction transaction);

	void updateTransaction(Transaction transaction);

	PaymentGroup findPaymentGroupById(int id);

	Transaction findTransactionByTrackingNumber(String trackingNo);

	List<Transaction> queryTransaction(TransactionQueryBean queryBean);

	int queryTransactionTotalAmount(TransactionQueryBean queryBean);
}
