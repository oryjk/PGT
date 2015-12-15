package com.pgt.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.loan.bean.Loan;
import com.pgt.loan.dao.LoanMapper;
import com.pgt.utils.PaginationBean;

@Service(value = "loanService")
public class LoanService {

	@Autowired
	private LoanMapper loanMapper;

	public List<Loan> findLoanIntros(Map<String, Object> searchKey, PaginationBean pageInfo) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.putAll(searchKey);
		long totalAmount = this.loanMapper.findLoansWithIntroTotalAmount(searchParams);
		pageInfo.setTotalAmount(totalAmount);
		searchParams.putAll(pageInfo.getMapValue());
		return this.loanMapper.findLoansWithIntro(searchParams);
	}


}
