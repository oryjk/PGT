package com.pgt.loan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pgt.loan.bean.Loan;
import com.pgt.loan.service.LoanService;
import com.pgt.utils.PaginationBean;

/**
 * 
 * @author samli
 *
 */

@RestController
@RequestMapping("/loan")
public class LoanController {

	@Resource(name = "loanService")
	private LoanService loanService;

	@RequestMapping(value = "/intros", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity queryIntros(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel,
			@RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
			@RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
			@RequestParam(value = "sortFiledName", required = false) String sortFiledName,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "notIncludeMock", required = false, defaultValue="false") String notIncludeMock,
			@RequestParam(value = "onlyIncludePopulate", required = false, defaultValue="false") String onlyIncludePopulate,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "status", required = false) String status
			) {
		
		if (!StringUtils.isNumeric(currentIndex) || !StringUtils.isNumeric(capacity)) {
			Map<String,String> error = new HashMap<String,String>();
			error.put("error", "currentIndex or capacity must be Numberic.");
			return new ResponseEntity(error, HttpStatus.OK);
			
		}
		
		// check internal user exist
		Map<String, Object> searchKey = new HashMap<String, Object>();
		
		searchKey.put("id", id);
		searchKey.put("category", category);
		searchKey.put("name", name);
		searchKey.put("status", status);
		
		if (Boolean.valueOf(notIncludeMock)) {
			searchKey.put("notIncludeMock", true);
		}
		
		if (Boolean.valueOf(onlyIncludePopulate)) {
			searchKey.put("onlyIncludePopulate", true);
		}
		
		String tempSortFiledName = sortFiledName;
		if (!"EARNINGS_RATE".equals(sortFiledName) && 
				!"SERVICE_FEE_RATE".equals(sortFiledName) &&
				!"COLLECT_END_DATE".equals(sortFiledName) &&
				!"AMOUNT".equals(sortFiledName) &&
				!"STATUS".equals(sortFiledName) &&
				!"IS_POPULATE".equals(sortFiledName)) {
			tempSortFiledName = "EARNINGS_RATE";
		}
		
		PaginationBean pageInfo = new PaginationBean();
		pageInfo.setCurrentIndex(Long.valueOf(currentIndex));
		pageInfo.setCapacity(Long.valueOf(capacity));
		pageInfo.setSortFiledName(tempSortFiledName);
		List<Loan> loans = getLoanService().findLoanIntros(searchKey, pageInfo);
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("otherParams", searchKey);
		result.put("pageInfo", pageInfo);
		result.put("resultList", loans);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	public LoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}
}
