package com.pgt.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.dao.TransactionLogMapper;
import com.pgt.utils.Transactionable;


@Service(value = "transactionLogService")
public class TransactionLogService extends Transactionable {
	
	@Autowired
	private TransactionLogMapper transactionLogMapper;
	
	
	
	

	public boolean createTransactionLog(TransactionLog transactionLog) {
		
		ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		try {
			
			  getTransactionLogMapper().createTransactionLog(transactionLog);
			  return true;
		} catch (Exception e) {
			setAsRollback();
			return false;
		} finally {
			commit();
		}
		
		
		
	}

	public boolean updateTransactionLog(TransactionLog transactionLog) {
		
		ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		try {
			
			  getTransactionLogMapper().updateTransactionLog(transactionLog);
			  return true;
		} catch (Exception e) {
			setAsRollback();
			return false;
		} finally {
			commit();
		}
	}
	
	public TransactionLog findByTrackingNo(String trackingNo) {
		return getTransactionLogMapper().findByTrackingNo(trackingNo);
	}
	
	public TransactionLog findById(int id) {
		return getTransactionLogMapper().findById(id);
	}

	public TransactionLogMapper getTransactionLogMapper() {
		return transactionLogMapper;
	}

	public void setTransactionLogMapper(TransactionLogMapper transactionLogMapper) {
		this.transactionLogMapper = transactionLogMapper;
	}

}
