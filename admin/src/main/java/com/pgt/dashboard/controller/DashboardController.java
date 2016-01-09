package com.pgt.dashboard.controller;

import com.pgt.dashboard.service.DashboardService;
import com.pgt.internal.controller.InternalTransactionBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 1/4/2016.
 */
@RestController
public class DashboardController extends InternalTransactionBaseController implements DashboardResponseConstant {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@Resource(name = "dashboardService")
	private DashboardService mDashboardService;

	@RequestMapping("/")
	public ModelAndView redirectDashboard() {
		ModelAndView mav = new ModelAndView("/dashboard/dashboard");
		// paid order count
		int orderCount = getDashboardService().queryPaidOrderCount();
		mav.addObject(ORDER_COUNT, orderCount);
		// commerce item count in paid order
		int itemCount = getDashboardService().queryPaidCommerceItemCount();
		mav.addObject(ITEM_COUNT, itemCount);
		// total sale amount

		double totalSales = getDashboardService().queryTotalSaleAmount();
		mav.addObject(TOTAL_SALES, totalSales);
		List<String> dashboardDates = getDashboardService().calculateDashboardStatisticDays();
		mav.addObject(DASHBOARD_DATES, dashboardDates);
		Map<String, Long> dateOrderCountMap = getDashboardService().queryPaidOrderCountDuringDate(dashboardDates);
		mav.addObject(DATE_ORDER_COUNT, dateOrderCountMap);
		Map<String, Long> dateItemCountMap = getDashboardService().queryPaidCommerceItemCountDuringDate(dashboardDates);
		mav.addObject(DATE_ITEM_COUNT, dateItemCountMap);
		Map<String, Double> dateSalesMap = getDashboardService().queryTotalSaleAmountDuringDate(dashboardDates);
		mav.addObject(DATE_TOTAL_SALES, dateSalesMap);
		return mav;
	}

	public DashboardService getDashboardService() {
		return mDashboardService;
	}

	public void setDashboardService(final DashboardService pDashboardService) {
		mDashboardService = pDashboardService;
	}
}
