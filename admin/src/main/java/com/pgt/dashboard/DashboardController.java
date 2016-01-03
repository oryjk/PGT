package com.pgt.dashboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Yove on 1/4/2016.
 */
@RestController
public class DashboardController {

	@RequestMapping("/index")
	public ModelAndView redirectDashboard() {
		return new ModelAndView("/dashboard/dashboard");
	}
}
