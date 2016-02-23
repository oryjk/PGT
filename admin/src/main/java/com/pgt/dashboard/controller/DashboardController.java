package com.pgt.dashboard.controller;

import com.pgt.dashboard.service.DashboardService;
import com.pgt.internal.controller.InternalTransactionBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public ModelAndView redirectDashboard(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		// permission verify
		boolean pass = verifyPermission(pRequest);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}

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

		mav.addObject(DASHBOARD_TODAY, getDashboardService().getDashboardToday());
		List<String> dashboardDates = getDashboardService().calculateDashboardStatisticDays();
		mav.addObject(DASHBOARD_DATES, dashboardDates);
		// paid order count during days
		Map<String, Long> dateOrderCountMap = getDashboardService().queryPaidOrderCountDuringDate(dashboardDates);
		mav.addObject(DATE_ORDER_COUNT, dateOrderCountMap);
		// commerce item count in paid order during days
		Map<String, Long> dateItemCountMap = getDashboardService().queryPaidCommerceItemCountDuringDate(dashboardDates);
		mav.addObject(DATE_ITEM_COUNT, dateItemCountMap);
		// total sale amount during days
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
