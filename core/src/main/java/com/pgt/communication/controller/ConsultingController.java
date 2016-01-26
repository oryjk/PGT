package com.pgt.communication.controller;

import com.pgt.common.bean.CommPaginationBean;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.service.ConsultingService;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.user.bean.User;
import com.pgt.utils.ResponseUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/consulting")
public class ConsultingController {

	@Autowired
	private ConsultingService consultingService;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultingController.class);


    /**
	 * query Consulting by ProductId
	 * @param productId
	 * @param modelAndView
	 * @param currentIndex
	 * @return
     */
	@RequestMapping(value = "/query/{currentIndex}/{productId}", method = RequestMethod.GET)
	public ModelAndView queryAllConsultingByProduct(@PathVariable("productId") Integer productId,
			ModelAndView modelAndView, @PathVariable("currentIndex") Long currentIndex) {
		modelAndView.setViewName(Constants.CONSULTING_PAGE);
		if (productId == null) {
			LOGGER.debug("The product id is empty");
			return modelAndView;
		}
		LOGGER.debug("The queryConsultingByProduct product id is {}",productId);
		ConsultingCustom consultingCustom = new ConsultingCustom();
		int total = consultingService.queryAllConsultingByProductCount(productId, consultingCustom);
		LOGGER.debug("The query total is {}",total);
		if (currentIndex == null) {
			currentIndex = 0L;
		}
		CommPaginationBean paginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(),
				currentIndex, total);
		consultingCustom.setPaginationBean(paginationBean);
		// 查询某个商品咨询列表 productId
		List<Consulting> consulting = consultingService.queryAllConsultingByProduct(productId, consultingCustom);
		modelAndView.addObject("consultings", consulting);
		// 带回分页的条件
		modelAndView.addObject("conPaginationBean", paginationBean);
		return modelAndView;
	}

	/**
	 * create consulting
	 * @param productId
	 * @param consulting
	 * @param request
	 * @param session
	 * @param response
     */
	@RequestMapping(value = "/createconsulting", method = RequestMethod.POST)
	public void createConsulting(Integer productId, Consulting consulting, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		if (productId == null) {
			LOGGER.debug("The product id is empty.");
			return;
		}
		Product product = productService.queryProduct(productId);
		if(ObjectUtils.isEmpty(product)){
			LOGGER.debug("The product is empty for product id is {}",productId);
			return;
		}
		// 获取ip 用户信息 商品id
		String ip = request.getRemoteAddr();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			LOGGER.debug("user is null  Redirect home page.");
			jo.put("logincheck", "no");
			ResponseUtils.renderJson(response, jo.toString());
			return;
		}
		consulting.setUser(user);
		consulting.setProduct(product);
		if (user != null) {
			consulting.setUser(user);
		}
		consulting.setIp(ip);
		LOGGER.debug("user ip is {}",ip);
		LOGGER.debug("user id is {}",user.getId());
		try {
			// 保存咨询的内容
			consultingService.createConsulting(consulting);
		} catch (Exception e) {
			LOGGER.error("The save consulting is error");
		}
		jo.put("message", "successful");
		ResponseUtils.renderJson(response, jo.toString());
	}

	// 6.进入会员中心所有的咨询记录页面
	public String queryAllConsultingByUserUI(HttpSession session) {
		User user = (User) session.getAttribute("user");
		Long userId = user.getId();
		consultingService.queryUserAllConsulting(userId);
		return "";
	}

	// 7.查看会员中心所有的咨询记录
	public ModelAndView queryAllConsultingByUser() {
		// 根据用户id，查询某个用户所有的咨询内容
		return null;
	}


	// 1.进入后台查询咨询列表页面
	public ModelAndView queryAllConsultingUI(ModelAndView modelAndView) {
		return modelAndView;
	}
	public ModelAndView queryAllConsulting() {
		// 查询所有的咨询内容 可以带查询条件
		return null;
	}
	// 2.删除一个咨询内容
	public String deleteConsulting(String id) {

		// 根据id删除咨询的内容
		return "";
	}
	public String deleteConsulting(Integer[] ids) {

		// 调用批量删除操作
		return "";
	}

	// 3.进入编辑页面
	public String updateConsultingUI(Boolean isShow) {
		// 更新显示状态
		return "";
	}

	// 4.修改咨询的显示内容
	public ModelAndView updateConsulting(Boolean isShow) {
		// 修改显示的状态
		return null;
	}

	// 5.进入回复界面
	public String replyConsultingUI(Integer id, ModelMap model) {
		// 设置回显的内容
		// 跳转到回复界面
		return "";
	}

	// 6.提交回复的内容
	public ModelAndView replyConsulting() {
		// 创建一个回复的内容
		return null;
	}

	// 7.删除回复
	public String deleteReply() {
		// 删除咨询回复的内容
		return "";
	}

}
