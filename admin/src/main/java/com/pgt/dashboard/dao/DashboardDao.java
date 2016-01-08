package com.pgt.dashboard.dao;

import com.pgt.base.mapper.SqlMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by Yove on 1/9/2016.
 */
@Repository(value = "dashboardDao")
public interface DashboardDao extends SqlMapper {

	int queryPaidOrderCount();

	int queryPaidCommerceItemCount();

	double queryTotalSaleAmount();
}
