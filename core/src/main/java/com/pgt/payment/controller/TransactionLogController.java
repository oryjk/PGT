package com.pgt.payment.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/transLog")
public class TransactionLogController {

	@Resource(name = "mockYeepay")
	private DirectYeePay mockYeePay;

	@Resource(name = "transactionLogService")
	private TransactionLogService transactionLogService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity test(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", 1L);
		params.put("orderId", 2L);
		params.put("paymentGroupId", 3L);
		params.put("transactionId", 4L);
		params.put("say", "helloworld");
		
		try {
			getMockYeePay().invoke(params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Map<String,String> ok = new HashMap<String,String>();
		ok.put("status", "ok");
		return new ResponseEntity(ok, HttpStatus.OK);
		
	}

	@RequestMapping(value = "/query")
	public ModelAndView query(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		String orderId = pRequest.getParameter("orderId");
		String uesrId = pRequest.getParameter("uesrId");
		String paymentGroupId = pRequest.getParameter("paymentGroupId");
		String transactionId = pRequest.getParameter("transactionId");
		String type  = pRequest.getParameter("type");
		String serviceName = pRequest.getParameter("serviceName");
		String startTimeStr = pRequest.getParameter("startTime");
		String endTimeStr = pRequest.getParameter("endTime");
		String currentIndexStr = pRequest.getParameter("currentIndex");
		String capacityStr = pRequest.getParameter("capacity");

		Date startTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(startTimeStr)) {
			try {
				startTime = DateUtils.parseDate(startTimeStr, "yyyyMM-dd HH:mm:ss");
			} catch (ParseException e) {
				throw new IllegalArgumentException("Start time format is not correct (yyyyMM-dd HH:mm:ss)");
			}
		}
		if (StringUtils.isNotBlank(endTimeStr)) {
			try {
				endTime = DateUtils.parseDate(endTimeStr, "yyyyMM-dd HH:mm:ss");
			} catch (ParseException e) {
				throw new IllegalArgumentException("Start time format is not correct (yyyyMM-dd HH:mm:ss)");
			}
		}
		int currentIndex = 0;
		if (StringUtils.isNotBlank(currentIndexStr)) {
			if (StringUtils.isNumeric(currentIndexStr)) {
				currentIndex = Integer.valueOf(currentIndexStr);
			}
 		}
		int capacity = PaginationBean.DEFAULT_CAPACITY;
		if (StringUtils.isNotBlank(capacityStr)) {
			if (StringUtils.isNumeric(capacityStr)) {
				capacity = Integer.valueOf(capacityStr);
			}
		}
		PaginationBean paginationBean = new PaginationBean();
		paginationBean.setCurrentIndex(currentIndex);
		paginationBean.setCapacity(capacity);
		List<TransactionLog> result = getTransactionLogService().queryTransactionLog(orderId, uesrId, paymentGroupId, transactionId, type, serviceName,
				startTime, endTime, paginationBean);


		return null;
	}

	public DirectYeePay getMockYeePay() {
		return mockYeePay;
	}

	public void setMockYeePay(DirectYeePay mockYeePay) {
		this.mockYeePay = mockYeePay;
	}

	public TransactionLogService getTransactionLogService() {
		return transactionLogService;
	}

	public void setTransactionLogService(TransactionLogService transactionLogService) {
		this.transactionLogService = transactionLogService;
	}
}
