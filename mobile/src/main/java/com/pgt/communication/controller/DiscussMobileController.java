package com.pgt.communication.controller;

import com.pgt.base.constans.MobileConstants;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;
import com.pgt.communication.service.DiscussService;
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
@RequestMapping("/mDiscuss")
public class DiscussMobileController extends BaseMobileController{

	@Autowired
	private DiscussService discussService;
	@Autowired
	private Configuration configuration;
	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscussMobileController.class);


	/**
	 * query discuss for a product
	 * @param productId
	 * @param currentIndex
     * @return json data for discussList,pageView for disPaginationBean
     */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public  Map<String,Object> queryDiscussByProductId(Integer productId, Long currentIndex) {
		LOGGER.debug("The method query discuss for product");
		Map<String,Object> responseMap = new HashMap<>();
		if (productId == null) {
			LOGGER.debug("product id is empty");
			return responseMobileFail(responseMap, "Product.empty");
		}
		DiscussCustom discussCustom = new DiscussCustom();
		int total = discussService.queryProductAllDiscussCount(productId);
		if (currentIndex == null) {
			currentIndex = 0L;
		}
		CommPaginationBean paginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(),
				currentIndex, total);
		if (currentIndex != null) {
			paginationBean.setCurrentIndex(currentIndex);
		}
		discussCustom.setPaginationBean(paginationBean);
		// 查询某个商品讨论列表 productId
		List<Discuss> discussList = discussService.queryProductAllDiscuss(productId, discussCustom);
		responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
		responseMap.put("discussList",discussList);
		responseMap.put("disPaginationBean",paginationBean);
		return responseMap;
	}

	/**
	 * createDiscuss
	 * @param productId
	 * @param discuss
	 * @param request
	 * @param session
     * @return fail or success
     */
	@RequestMapping(value = "/createDiscuss", method = RequestMethod.POST)
	public  Map<String,Object> createdDiscuss(Integer productId,Discuss discuss,
			HttpServletRequest request, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<>();
		if (ObjectUtils.isEmpty(productId)) {
			LOGGER.debug("The product id is empty.");
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
			LOGGER.debug("The user is empty");
			return responseMobileFail(responseMap, "User.empty");
		}
		discuss.setUser(user);
		discuss.setProduct(product);
		discuss.setIp(ip);
		try {
			discussService.createDiscuss(discuss);
			LOGGER.debug("create a discuss");
		} catch (Exception e) {
			LOGGER.debug("The save discuss is error");
			return responseMobileFail(responseMap, "save.error");
		}
		responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
        return responseMap;
	}

}
