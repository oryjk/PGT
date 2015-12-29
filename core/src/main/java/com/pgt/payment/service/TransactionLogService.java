package com.pgt.payment.service;

import com.pgt.payment.bean.TransactionLogQueryBean;
import com.pgt.utils.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.dao.TransactionLogMapper;
import com.pgt.utils.Transactionable;

import java.util.Date;
import java.util.List;


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


	public List<TransactionLog> queryTransactionLog(String orderId, String userId, String paymentGroupId,
													 String transactionId, String type, String serviceName,
													 Date startTime, Date endTime, PaginationBean paginationBean) {
		TransactionLogQueryBean bean = new TransactionLogQueryBean();
		if (StringUtils.isNotBlank(orderId)) {
			if (!StringUtils.isNumeric(orderId)) {
				throw new IllegalArgumentException("order id is not numeric");
			}
			bean.setOrderId(Integer.valueOf(orderId));
		}

		if (StringUtils.isNotBlank(userId)) {
			if (!StringUtils.isNumeric(userId)) {
				throw new IllegalArgumentException("order id is not numeric");
			}
			bean.setUserId(Integer.valueOf(userId));
		}

		if (StringUtils.isNotBlank(paymentGroupId)) {
			if (!StringUtils.isNumeric(paymentGroupId)) {
				throw new IllegalArgumentException("order id is not numeric");
			}
			bean.setPaymentGroupId(Integer.valueOf(paymentGroupId));
		}
		if (StringUtils.isNotBlank(transactionId)) {
			if (!StringUtils.isNumeric(transactionId)) {
				throw new IllegalArgumentException("order id is not numeric");
			}
			bean.setTransactionId(Integer.valueOf(transactionId));
		}
		bean.setType(type);
		bean.setServiceName(serviceName);
		bean.setStartTime(startTime);
		bean.setEndTime(endTime);
		bean.setPaginationBean(paginationBean);
		int totalAmount = getTransactionLogMapper().queryTransactionLogTotalAmount(bean);
		paginationBean.setTotalAmount(totalAmount);
		return getTransactionLogMapper().queryTransactionLog(bean);
	}



	public TransactionLogMapper getTransactionLogMapper() {
		return transactionLogMapper;
	}





	public void setTransactionLogMapper(TransactionLogMapper transactionLogMapper) {
		this.transactionLogMapper = transactionLogMapper;
	}

}
