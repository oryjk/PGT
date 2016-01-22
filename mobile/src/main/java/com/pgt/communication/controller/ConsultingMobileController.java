package com.pgt.communication.controller;

import com.pgt.base.constans.MobileConstans;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.service.ConsultingService;
import com.pgt.configuration.Configuration;
import com.pgt.constant.UserConstant;
import com.pgt.base.controller.BaseMobileController;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/mConsulting")
public class ConsultingMobileController extends BaseMobileController{

	@Autowired
	private ConsultingService consultingService;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultingMobileController.class);
	/**
	 * query consulting for a product
	 * @param productId
	 * @param currentIndex
	 * @return json data for consulting,pageView for disPaginationBean
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public  Map<String,Object> queryAllConsultingByProduct(Integer productId, Long currentIndex) {
		Map<String,Object> responseMap= new HashMap<>();
		if (productId == null) {
			LOGGER.debug("The product id is empty.");
			return responseMobileFail(responseMap, "Product.empty");
		}
		ConsultingCustom consultingCustom = new ConsultingCustom();
		int total = consultingService.queryAllConsultingByProductCount(productId, consultingCustom);
		if (currentIndex == null) {
			currentIndex = 0L;
		}
		CommPaginationBean paginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(),
				currentIndex, total);
		consultingCustom.setPaginationBean(paginationBean);
		List<Consulting> consultings = consultingService.queryAllConsultingByProduct(productId, consultingCustom);
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		responseMap.put("consultings",consultings);
		responseMap.put("conPaginationBean",paginationBean);
		return responseMap;
	}

	/**
	 * create a consulting
	 * @param productId
	 * @param consulting
	 * @param request
	 * @param session
     * @return fail or success
     */
	@RequestMapping(value = "/createconsulting", method = RequestMethod.POST)
	public  Map<String,Object> createConsulting(Integer productId, Consulting consulting, HttpServletRequest request,
			HttpSession session) {

		Map<String,Object> responseMap= new HashMap<String,Object>();
		if (productId == null) {
			LOGGER.debug("The product id is empty");
			return responseMobileFail(responseMap, "Product.empty");
		}
		Product product = productService.queryProduct(productId);
		if(ObjectUtils.isEmpty(product)){
			LOGGER.debug("The product id is empty");
			return responseMobileFail(responseMap, "Product.empty");
		}
		String ip = request.getRemoteAddr();
		LOGGER.debug("The access ip is {}",ip);
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if(ObjectUtils.isEmpty(user)){
			LOGGER.debug("User.empty");
			return responseMobileFail(responseMap, "User.empty");
		}
		consulting.setUser(user);
		consulting.setProduct(product);
		consulting.setIp(ip);
		try {
			consultingService.createConsulting(consulting);
			LOGGER.debug("create a consulting");
		} catch (Exception e) {
			LOGGER.debug("The save consulitng is error");
			return responseMobileFail(responseMap, "save.error");
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
	}
}
