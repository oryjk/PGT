package com.pgt.dashboard.dao;

import com.pgt.base.mapper.SqlMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 1/9/2016.
 */
@Repository(value = "dashboardDao")
public interface DashboardDao extends SqlMapper {

	int queryPaidOrderCount();

	int queryPaidCommerceItemCount();

	double queryTotalSaleAmount();

	Map<String, Long> queryPaidOrderCountDuringDate(List<String> pDateStrings);

	Map<String, Long> queryPaidCommerceItemCountDuringDate(List<String> pDateStrings);

	Map<String, Double> queryTotalSaleAmountDuringDate(List<String> pDateStrings);
}
