package com.pgt.integration.alipay.dao;

import org.springframework.stereotype.Repository;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.integration.alipay.bean.AlipayResult;

@Repository
public interface AlipayResultMapper extends SqlMapper {

	AlipayResult findAlipayResult(int id);

	void createAlipayResult(AlipayResult alipayResult);
}
