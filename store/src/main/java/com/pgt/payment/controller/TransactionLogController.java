package com.pgt.payment.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.loan.service.LoanService;



@RestController
@RequestMapping("/transLog")
public class TransactionLogController {

	@Resource(name = "mockYeepay")
	private DirectYeePay mockYeePay;
	
	@Resource(name = "loanService")
	private LoanService loanService;

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
			getMockYeePay().invok(params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Map<String,String> ok = new HashMap<String,String>();
		ok.put("status", "ok");
		return new ResponseEntity(ok, HttpStatus.OK);
		
	}

	public DirectYeePay getMockYeePay() {
		return mockYeePay;
	}

	public void setMockYeePay(DirectYeePay mockYeePay) {
		this.mockYeePay = mockYeePay;
	}

	public LoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}
	
	

}
