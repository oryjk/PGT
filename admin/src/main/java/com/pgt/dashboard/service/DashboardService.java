package com.pgt.dashboard.service;

import com.pgt.dashboard.dao.DashboardDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 1/9/2016.
 */
@Service(value = "dashboardService")
public class DashboardService {

	@Resource(name = "dashboardDao")
	private DashboardDao mDashboardDao;


	public int queryPaidOrderCount() {
		return getDashboardDao().queryPaidOrderCount();
	}

	public int queryPaidCommerceItemCount() {
		return getDashboardDao().queryPaidCommerceItemCount();
	}

	public DashboardDao getDashboardDao() {
		return mDashboardDao;
	}

	public void setDashboardDao(final DashboardDao pDashboardDao) {
		mDashboardDao = pDashboardDao;
	}

	public double queryTotalSaleAmount() {
		return getDashboardDao().queryTotalSaleAmount();
	}
}
