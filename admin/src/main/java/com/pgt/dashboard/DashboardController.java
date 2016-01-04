package com.pgt.dashboard;

import com.pgt.internal.controller.InternalTransactionBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Yove on 1/4/2016.
 */
@RestController
public class DashboardController extends InternalTransactionBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("/dashboard")
	public ModelAndView redirectDashboard() {
		return new ModelAndView("/dashboard/dashboard");
	}


}
