package com.pgt.loan.dao;

import java.util.List;
import java.util.Map;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.loan.bean.Loan;

public interface LoanMapper extends SqlMapper {

	/**
	 * 
	 * @param searchKey
	 *            name, category, notIncludeMock, id...
	 * @param pageInfo
	 * @return loan with intro(only load data in loan tale)
	 */
	public List<Loan> findLoansWithIntro(Map<String, Object> searchParams);
	// {
	// Map<String, Object> searchParams = new HashMap<String, Object>();
	// searchParams.putAll(searchKey);
	// searchParams.putAll(pageInfo.getMapValue());
	// return
	// this.getSqlSessionTemplate().selectList("com.pgt.mapper.LoanMapper.findLoansWithIntro",
	// searchParams);
	// }

	public Long findLoansWithIntroTotalAmount(Map<String, Object> searchParams);
	// {
	//
	// return
	// this.getSqlSessionTemplate().selectOne("com.pgt.mapper.LoanMapper.findLoansWithIntroTotalAmount",
	// searchParams);
	// }

	/**
	 * 
	 * @param searchKey
	 *            name, category, notIncludeMock, id...
	 * @param pageInfo
	 * @return loan with intro(also load related data)
	 */
	//public List<Loan> findLoansWithDetail(Map<String, Object> searchKey, PaginationBean pageInfo);
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
