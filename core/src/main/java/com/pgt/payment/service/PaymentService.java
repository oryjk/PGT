package com.pgt.payment.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import com.pgt.payment.bean.*;
import com.pgt.utils.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.cart.bean.Order;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.dao.PaymentMapper;
import com.pgt.utils.Transactionable;

@Service(value = "paymentService")
public class PaymentService extends Transactionable {

	@Autowired
	private PaymentMapper paymentMapper;

	private TransactionReportConfig transactionReportConfig;

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


	public List<Transaction> queryTransaction(TransactionQueryBean queryBean, PaginationBean paginationBean) {
		queryBean.setPaginationBean(paginationBean);
		int totalAmount = getPaymentMapper().queryTransactionTotalAmount(queryBean);
		paginationBean.setTotalAmount(totalAmount);

		return getPaymentMapper().queryTransaction(queryBean);
	}


	public List<Transaction> queryTransaction(Integer orderId, Integer paymentType, Integer state, String trackNo,
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

	public void generateReport(Integer orderId, Integer paymentType, Integer state, String trackNo,
							   Date startTime, Date endTime,OutputStream out) throws IOException {
		TransactionQueryBean queryBean = new TransactionQueryBean();
		queryBean.setOrderId(orderId);
		queryBean.setPaymentType(paymentType);
		queryBean.setState(state);
		queryBean.setTrackNo(trackNo);
		queryBean.setStartTime(startTime);
		queryBean.setEndTime(endTime);
		PaginationBean paginationBean = new PaginationBean();
		paginationBean.setCurrentIndex(0);
		paginationBean.setCapacity(getTransactionReportConfig().getDataFetchSize());
		int totalAmount = getPaymentMapper().queryTransactionTotalAmount(queryBean);
		paginationBean.setTotalAmount(totalAmount);
		queryBean.setPaginationBean(paginationBean);

		SXSSFWorkbook wb =  new SXSSFWorkbook(getTransactionReportConfig().getRowBufferSize()); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet();
		wb.setSheetName(0,"交易报表");
		int rowNum = 0;
		//
		Row header = sh.createRow( rowNum);
		header.createCell(0).setCellValue("订单编号");
		header.createCell(1).setCellValue("支付类型");
		header.createCell(2).setCellValue("支付金额");
		header.createCell(3).setCellValue("支付状态");
		header.createCell(4).setCellValue("交易时间");
		header.createCell(5).setCellValue("交易号");

		rowNum++;
		while (paginationBean.getCurrentIndex() < paginationBean.getMaxIndex() + 1) {
			List<Transaction> transactions = getPaymentMapper().queryTransaction(queryBean);
			for(Transaction transaction : transactions){
				Row row = sh.createRow( rowNum);
				String orderIdValue = "";
				String paymentTypeValue = "";
				String amountValue = String.valueOf(transaction.getAmount());
				String statusValue = "处理中";
				String transactionTimeValue = "";
				String trackingNoValue = "";
				if (null != transaction.getOrderId()) {
					orderIdValue = String.valueOf(transaction.getOrderId());
				}
				// TODO PAYMENT TYPE
				if (PaymentConstants.PAYMENT_STATUS_FAILED == transaction.getStatus()) {
					statusValue = "失败";
				}
				if (PaymentConstants.PAYMENT_STATUS_SUCCESS == transaction.getStatus()) {
					statusValue = "成功";
				}
				if(null != transaction.getTransactionTime()) {
					transactionTimeValue = DateFormatUtils.format(transaction.getTransactionTime(), "yyyy-MM-dd HH:mm:ss");
				}
				if (StringUtils.isNotBlank(transaction.getTrackingNo())) {
					trackingNoValue = transaction.getTrackingNo();
				}
				row.createCell(0).setCellValue(orderIdValue);
				row.createCell(1).setCellValue(paymentTypeValue);
				row.createCell(2).setCellValue(amountValue);
				row.createCell(3).setCellValue(statusValue);
				row.createCell(4).setCellValue(transactionTimeValue);
				row.createCell(5).setCellValue(trackingNoValue);
		 		rowNum++;
			}

			paginationBean.setCurrentIndex(paginationBean.getCurrentIndex() + 1);
		}
		wb.write(out);
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

	public TransactionReportConfig getTransactionReportConfig() {
		return transactionReportConfig;
	}

	public void setTransactionReportConfig(TransactionReportConfig transactionReportConfig) {
		this.transactionReportConfig = transactionReportConfig;
	}
}
