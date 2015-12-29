package com.pgt.payment.dao;

import com.pgt.payment.bean.TransactionLogQueryBean;
import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.payment.bean.TransactionLog;

import java.util.List;

@Component
public interface TransactionLogMapper extends SqlMapper {
	
	int createTransactionLog(final TransactionLog transactionlog);
	
	int updateTransactionLog(final TransactionLog transactionlog);
	
	TransactionLog findById(final int id);

	TransactionLog findByTrackingNo(String trackingNo);

	List<TransactionLog> queryTransactionLog(TransactionLogQueryBean bean);

	int queryTransactionLogTotalAmount(TransactionLogQueryBean bean);
}
