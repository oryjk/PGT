package com.pgt.mobile.communication.controller;

import com.pgt.common.bean.CommPaginationBean;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.service.ConsultingService;
import com.pgt.configuration.Configuration;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mConsulting")
public class ConsultingMobileController extends BaseMobileController{

	@Autowired
	private ConsultingService consultingService;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ProductService productService;

	@Autowired
	private SimpleCacheManager cacheManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultingMobileController.class);


	// 1.查看某个商品下的咨询列表
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public  Map<String,Object> queryAllConsultingByProduct(Integer productId, Long currentIndex) {
		Map<String,Object> responseMap= new HashMap<String,Object>();
		if (productId == null) {
			LOGGER.warn("The product id is empty.");
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
		// 查询某个商品咨询列表 productId
		List<Consulting> consultings = consultingService.queryAllConsultingByProduct(productId, consultingCustom);
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		responseMap.put("consultings",consultings);
		responseMap.put("conPaginationBean",paginationBean);
		return responseMap;
	}

	// 2.提交咨询的内容
	@RequestMapping(value = "/createconsulting", method = RequestMethod.POST)
	public  Map<String,Object> createConsulting(Integer productId, String phoneId,Consulting consulting, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {

		Map<String,Object> responseMap= new HashMap<String,Object>();
		if (productId == null) {
			LOGGER.warn("The product id is empty");
			return responseMobileFail(responseMap, "Product.empty");
		}
		Product product = productService.queryProduct(productId);
		// 获取ip 用户信息 商品id
		String ip = request.getRemoteAddr();

		if(StringUtils.isEmpty(phoneId)){
			return responseMobileFail(responseMap, "PhoneId.empty");
		}
		Cache cache = cacheManager.getCache(MobileConstans.PHONE_USER);
		Cache.ValueWrapper valueWrapper = cache.get(phoneId);
		if (ObjectUtils.isEmpty(valueWrapper)) {
			return responseMobileFail(responseMap, "User.empty");
		}
		User user= (User) valueWrapper.get();
		consulting.setUser(user);
		consulting.setProduct(product);
		if (user != null) {
			consulting.setUser(user);
		}
		consulting.setIp(ip);
		try {
			// 保存咨询的内容
			consultingService.createConsulting(consulting);
		} catch (Exception e) {
			LOGGER.warn("The save consulitng is error");
			return responseMobileFail(responseMap, "save.error");
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
	}
}
