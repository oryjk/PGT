package com.pgt.order.controller;

import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.order.service.B2COrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yove on 12/21/2015.
 */
@RequestMapping("/order")
@RestController
public class B2COrderController extends InternalTransactionBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderController.class);

	private B2COrderService mB2COrderService;

	@RequestMapping(value = "/order-list")//, method = RequestMethod.GET)
	public ModelAndView listInternalUser(HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
			@RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "priceBeg", required = false) String priceBeg,
			@RequestParam(value = "priceEnd", required = false) String priceEnd,
			@RequestParam(value = "submitTimeBeg", required = false) String submitTimeBeg,
			@RequestParam(value = "submitTimeEnd", required = false) String submitTimeEnd
	) {
		long ciLong = RepositoryUtils.safeParse2Long(currentIndex);
		long caLong = RepositoryUtils.safeParse2Long(capacity);
		LOGGER.debug("Query orders with index: {}, capacity: {}", ciLong, caLong);

		// InternalPaginationBuilder ipb = new InternalPaginationBuilder();
		// InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword).createInternalPagination();
		// getInternalUserService().queryInternalUserPage(pagination);
		ModelAndView mav = new ModelAndView("/internal/iu-list");
		// mav.addObject(ResponseConstant.INTERNAL_USER_PAGE, pagination);
		return mav;
	}

	public B2COrderService getB2COrderService() {
		return mB2COrderService;
	}

	public void setB2COrderService(final B2COrderService pB2COrderService) {
		mB2COrderService = pB2COrderService;
	}
}
