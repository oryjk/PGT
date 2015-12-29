package com.pgt.payment.dao;

import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.payment.bean.TransactionLog;

@Component
public interface TransactionLogMapper extends SqlMapper {
	
	public int createTransactionLog(final TransactionLog transactionlog);
	
	public int updateTransactionLog(final TransactionLog transactionlog);
	
	public TransactionLog findById(final int id);

	public TransactionLog findByTrackingNo(String trackingNo);
	
	public TransactionLog findLastLogByOrderId(long orderId);

}
