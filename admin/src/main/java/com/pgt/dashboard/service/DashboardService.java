package com.pgt.dashboard.service;

import com.pgt.dashboard.dao.DashboardDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yove on 1/9/2016.
 */
@Service(value = "dashboardService")
public class DashboardService {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Resource(name = "dashboardDao")
	private DashboardDao mDashboardDao;

	private int mDashboardStatisticDays = 7;

	public int queryPaidOrderCount() {
		return getDashboardDao().queryPaidOrderCount();
	}

	public Map<String, Long> queryPaidOrderCountDuringDate(List<String> pDateStrings) {
		if (CollectionUtils.isEmpty(pDateStrings)) {
			return Collections.EMPTY_MAP;
		}
		return getDashboardDao().queryPaidOrderCountDuringDate(pDateStrings);
	}

	public int queryPaidCommerceItemCount() {
		return getDashboardDao().queryPaidCommerceItemCount();
	}

	public Map<String, Long> queryPaidCommerceItemCountDuringDate(List<String> pDateStrings) {
		if (CollectionUtils.isEmpty(pDateStrings)) {
			return Collections.EMPTY_MAP;
		}
		return getDashboardDao().queryPaidCommerceItemCountDuringDate(pDateStrings);
	}

	public double queryTotalSaleAmount() {
		return getDashboardDao().queryTotalSaleAmount();
	}

	public Map<String, Double> queryTotalSaleAmountDuringDate(List<String> pDateStrings) {
		if (CollectionUtils.isEmpty(pDateStrings)) {
			return Collections.EMPTY_MAP;
		}
		return getDashboardDao().queryTotalSaleAmountDuringDate(pDateStrings);
	}

	public List<String> calculateDashboardStatisticDays() {
		List<String> dates = new ArrayList<>(getDashboardStatisticDays());
		Calendar cal = Calendar.getInstance();
		// get calendar instance of statistic days before
		for (int i = 0; i < getDashboardStatisticDays(); i++) {
			cal.add(Calendar.DAY_OF_YEAR, -1);
			String dateString = DATE_FORMAT.format(cal.getTime());
			dates.add(dateString);
		}
		// reverse to let date asc
		Collections.reverse(dates);
		return dates;
	}

	public DashboardDao getDashboardDao() {
		return mDashboardDao;
	}

	public void setDashboardDao(final DashboardDao pDashboardDao) {
		mDashboardDao = pDashboardDao;
	}

	public int getDashboardStatisticDays() {
		return mDashboardStatisticDays;
	}

	public void setDashboardStatisticDays(final int pDashboardStatisticDays) {
		mDashboardStatisticDays = pDashboardStatisticDays;
	}
}
