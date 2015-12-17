package com.pgt.integration.yeepay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgt.internal.controller.InternalUserController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.integration.yeepay.notification.service.CompleteTransactionNotificationHandler;
import com.pgt.integration.yeepay.notification.service.YeepayNotificationHandler;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.PaymentService;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import com.yeepay.g3.utils.security.cfca.SignUtil;

@RestController
@RequestMapping("/yeepay")
public class YeePayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternalUserController.class);

	@Resource(name = "yeePayConfig")
	private YeePayConfig config;

	@Resource(name = "transactionLogService")
	private TransactionLogService transactionLogService;
	
	@Autowired
    private URLConfiguration urlConfiguration;
	
	@Resource(name = "paymentService")
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	@Resource(name = "completeTransactionNotificationHandler")
	private CompleteTransactionNotificationHandler completeTransactionNotificationHandler;

	@RequestMapping(value = "/getSgin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getSign(HttpServletRequest pRequest, HttpServletResponse pResponse, Model pModel) {

		try {

			pRequest.setCharacterEncoding("UTF-8");

			String serviceName = pRequest.getParameter(YeePayConstants.PARAM_SERVICE_NAME);
			if (StringUtils.isBlank(serviceName)) {
				throw new IllegalArgumentException(YeePayConstants.PARAM_SERVICE_NAME);
			}

			if (null == getConfig().getRequestParamConfig()) {
				throw new IllegalArgumentException("Invalid RequestParamConfig");
			}
			Map<String, String> paramConfig = getConfig().getRequestParamConfig().get(serviceName);
			if (null == paramConfig) {
				throw new IllegalArgumentException("No RequestParamConfig for service: " + serviceName);
			}
			if (null == getConfig().getRequestUrl()) {
				throw new IllegalArgumentException("No RequestUrl");
			}
			String requestURL = getConfig().getRequestUrl().get(serviceName);
			if (StringUtils.isBlank(requestURL)) {
				throw new IllegalArgumentException("No RequestUrl.");
			}
			
			User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
			// if there is other api has additional loigc need add a interface here.
			if (YeePayConstants.SERVICE_NAME_TOREGISTER.equals(serviceName)) {
				String name = pRequest.getParameter("realName");
				String id = pRequest.getParameter("idCardNo");
				user.setYeepayUserName(name);
				user.setYeepayUserId(id);
				getUserService().updateUser(user);
			}
			
			
			TransactionLog transactionLog = new TransactionLog();
			transactionLog.setPaymentType(YeePayConstants.PAYMENT_TYPE);
			transactionLog.setServiceName(serviceName);
			
			if (null != user) {
				transactionLog.setUserId(user.getId());
			}

			Map<String, Object> params = extractParams(pRequest, serviceName, paramConfig, transactionLog);
			String requestXML = YeePayHelper.generateRequestXml(getConfig(), params);
			String sign = YeePayHelper.generateSign(getConfig(), requestXML);
			Map<String, String> responseContent = new HashMap<String, String>();
			StringBuilder outBoundBuilder = new StringBuilder();
			outBoundBuilder.append("serviceName:\n");
			outBoundBuilder.append(serviceName);
			outBoundBuilder.append("\n");
			outBoundBuilder.append("req: \n");
			outBoundBuilder.append(requestXML);
			outBoundBuilder.append("\n");
			outBoundBuilder.append("sign: \n");
			outBoundBuilder.append(sign);
			transactionLog.setOutbound(outBoundBuilder.toString());
			transactionLog.setOutboundTime(new Date());
			LOGGER.info(outBoundBuilder.toString());
			getTransactionLogService().updateTransactionLog(transactionLog);

			responseContent.put("status", "200");
			responseContent.put("requestXML", requestXML);
			responseContent.put("sign", sign);
			responseContent.put("requestURL", requestURL);
			return new ResponseEntity(responseContent, HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error("Ajax get sign failed",e);
			String error = e.getMessage();
			Map<String, String> responseContent = new HashMap<String, String>();
			responseContent.put("status", "200");
			responseContent.put("error", error);
			return new ResponseEntity(responseContent, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/yeepayForm", method = RequestMethod.GET)
	public ModelAndView yeepayForm(HttpServletRequest pRequest, HttpServletResponse pResponse) {

		String serviceName = pRequest.getParameter(YeePayConstants.PARAM_SERVICE_NAME);
		if (StringUtils.isBlank(serviceName)) {
			throw new IllegalArgumentException("serviceName is blank");
		}
		if (null == getConfig().getServiceJspPath()) {
			throw new IllegalArgumentException("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=serviceJspPath ");
		}
		String jspPath = getConfig().getServiceJspPath().get(serviceName).get(YeePayConstants.PARAM_NAME_FORM_JSP);
		if (StringUtils.isBlank(jspPath)) {
			throw new IllegalArgumentException("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=serviceJspPath, key=" + serviceName );
		}
		ModelAndView mav = new ModelAndView(jspPath);
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null != user) {
			mav.addObject(YeePayConstants.PARAM_NAME_USER_ID, user.getId());
			mav.addObject(YeePayConstants.PARAM_NAME_PLATFORM_USER_NO,
					YeePayHelper.generateOutboundUserNo(getConfig(), user.getId()));
			mav.addObject(YeePayConstants.PARAM_NAME_YEEPAY_CONFIG, getConfig());
		}
		mav.addObject(YeePayConstants.PARAM_SERVICE_NAME, serviceName);
		return mav;
	}

	@RequestMapping(value = "/yeepayCallback", method = RequestMethod.POST)
	public ModelAndView yeepayCallback(HttpServletRequest pRequest, HttpServletResponse pResponse) {

		String inboundXML = pRequest.getParameter(YeePayConstants.PARAM_NAME_RESP);
		String inboundSign = pRequest.getParameter(YeePayConstants.PARAM_NAME_SIGN);
		
		boolean pass = SignUtil.verifySign(inboundXML, inboundSign, "yeepay.com");
		if (!pass) {
			new IllegalArgumentException("Not pass sign validation");
		}

		Map<String, String> responseParams = YeePayHelper.parseXml(inboundXML);
		String requestNoStr = responseParams.get(YeePayConstants.PARAM_NAME_REQUEST_NO);
		int requestNo = YeePayHelper.parseRequestId(getConfig(), requestNoStr);
		TransactionLog transactionLog = getTransactionLogService().findById(requestNo);
		StringBuilder inboundBuilder = new StringBuilder();
		inboundBuilder.append("inboundXML: \n").append(inboundXML).append("\ninboudSign: \n").append(inboundSign);
		transactionLog.setInbound(inboundBuilder.toString());
		transactionLog.setInboundTime(new Date());
		getTransactionLogService().updateTransactionLog(transactionLog);

		if (null == getConfig().getNotificationHandler()) {
			new IllegalArgumentException("NotificationHandle not config.");
		}

		String serviceName = transactionLog.getServiceName();
		if (StringUtils.isBlank(serviceName)) {
			new IllegalArgumentException("serviceName is blank");
		}

		// use service name to get handler from config. and call handler.
		YeepayNotificationHandler notificationHandler = getConfig().getNotificationHandler().get(serviceName);
		if (null == notificationHandler) {
			new IllegalArgumentException("no notificationHandler for serviceName: "  + serviceName);
		}

		Map<String, String> inboundParam = YeePayHelper.parseXml(inboundXML);
		String jspPath = null;
		ModelAndView mav = null;
		try {
			jspPath = getConfig().getServiceJspPath().get(serviceName).get(YeePayConstants.PARAM_NAME_SUCCESS_JSP);
			mav = new ModelAndView(jspPath);
			notificationHandler.handleCallback(inboundParam, transactionLog);
		} catch (YeePayException e) {
			jspPath = getConfig().getServiceJspPath().get(serviceName).get(YeePayConstants.PARAM_NAME_ERROR_JSP);
			mav = new ModelAndView(jspPath);
			mav.addObject(YeePayConstants.PARAM_NAME_ERROR, e);
		}

		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null != user) {
			mav.addObject(YeePayConstants.PARAM_NAME_USER_ID, user.getId());
		}
		mav.addObject(YeePayConstants.PARAM_SERVICE_NAME, serviceName);
		return mav;
	}

	@RequestMapping(value = "/yeepayNotify", method = RequestMethod.POST)
	public ResponseEntity yeepayNotify(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		String inboundXML = pRequest.getParameter(YeePayConstants.PARAM_NAME_NOTIFY);
		String inboundSign = pRequest.getParameter(YeePayConstants.PARAM_NAME_SIGN);
		
		boolean pass = SignUtil.verifySign(inboundXML, inboundSign, "yeepay.com");
		if (!pass) {
			return new ResponseEntity(HttpStatus.OK);
		}

		Map<String, String> responseParams = YeePayHelper.parseXml(inboundXML);
		String requestNoStr = responseParams.get(YeePayConstants.PARAM_NAME_REQUEST_NO);
		int requestNo = YeePayHelper.parseRequestId(getConfig(), requestNoStr);
		TransactionLog transactionLog = getTransactionLogService().findById(requestNo);
		StringBuilder inboundBuilder = new StringBuilder();
		inboundBuilder.append("inboundXML: \n").append(inboundXML).append("\ninboudSign: \n").append(inboundSign);
		transactionLog.setInbound(inboundBuilder.toString());
		transactionLog.setInboundTime(new Date());
		getTransactionLogService().updateTransactionLog(transactionLog);

		if (null == getConfig().getNotificationHandler()) {
			LOGGER.error("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=notificationHandler");
			return new ResponseEntity(HttpStatus.OK);
		}

		String serviceName = transactionLog.getServiceName();
		if (StringUtils.isBlank(serviceName)) {
			LOGGER.error("serviceName is blank");
			return new ResponseEntity(HttpStatus.OK);
		}

		// use service name to get handler from config. and call handler.
		YeepayNotificationHandler notificationHandler = getConfig().getNotificationHandler().get(serviceName);
		if (null == notificationHandler) {
			LOGGER.error("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=notificationHandler, key=" + serviceName);
			return new ResponseEntity(HttpStatus.OK);
		}

		Map<String, String> inboundParam = YeePayHelper.parseXml(inboundXML);
		try {
			notificationHandler.handleNotify(inboundParam, transactionLog);
			
			return new ResponseEntity(YeePayConstants.SUCCESS, HttpStatus.OK);
		} catch (YeePayException e) {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/yeepayB2cPay", method = RequestMethod.GET)
	public ModelAndView b2cPay(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		User user = (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
		if (null == user) {
			ModelAndView modelAndView = new ModelAndView("redirect:" +getUrlConfiguration().getLoginPage());
			return modelAndView;
		}
		Order order = (Order) pRequest.getSession().getAttribute(CartConstant.CURRENT_ORDER);
		if (null == order) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingCart/cart");
			return modelAndView;
		}
		
		
		String jspPath = null;
		ModelAndView mav = null;
		try {
			getPaymentService().ensureTransaction();
			PaymentGroup paymentGroup = new PaymentGroup();
			Date now = new Date();
			Long orderId = Long.valueOf(order.getId());
			paymentGroup.setOrderId(orderId);
			paymentGroup.setAmount(order.getTotal());
			paymentGroup.setCreateDate(now);
			paymentGroup.setUpdateDate(now);
			paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
			paymentGroup.setType(PaymentConstants.PAYMENT_TYPE_YEEPAY);
			getPaymentService().maintainPaymentGroup(paymentGroup);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(order.getTotal());
			transaction.setCreationDate(now);
			transaction.setUpdateDate(now);
			transaction.setOrderId(orderId);
			transaction.setPaymentGroupId(paymentGroup.getId());
			transaction.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
			getPaymentService().createTransaction(transaction);

			TransactionLog transactionLog = new TransactionLog();
			transactionLog.setUserId(Long.valueOf(order.getUserId()));
			transactionLog.setOrderId(Long.valueOf(order.getId()));
			transactionLog.setTransactionId(transaction.getId());
			transactionLog.setPaymentGroupId(paymentGroup.getId());

			transactionLog.setPaymentType(YeePayConstants.PAYMENT_TYPE);
			transactionLog.setServiceName(YeePayConstants.SERVICE_NAME_TOCPTRANSACTION);

			getTransactionLogService().createTransactionLog(transactionLog);

			Map<String, Object> paramMap = getPaymentParamMap(order, transactionLog, pRequest, pResponse);
			String outboundXML = YeePayHelper.generateRequestXml(getConfig(), paramMap);
			String sign = YeePayHelper.generateSign(getConfig(), outboundXML);
			StringBuilder outBoundBuilder = new StringBuilder();
			outBoundBuilder.append("serviceName:\n");
			outBoundBuilder.append(YeePayConstants.SERVICE_NAME_TOCPTRANSACTION);
			outBoundBuilder.append("\n");
			outBoundBuilder.append("req: \n");
			outBoundBuilder.append(outboundXML);
			outBoundBuilder.append("\n");
			outBoundBuilder.append("sign: \n");
			outBoundBuilder.append(sign);
			transactionLog.setOutbound(outBoundBuilder.toString());
			transactionLog.setOutboundTime(new Date());
			getTransactionLogService().updateTransactionLog(transactionLog);
			jspPath = getConfig().getServiceJspPath().get(YeePayConstants.SERVICE_NAME_TOCPTRANSACTION)
					.get(YeePayConstants.PARAM_NAME_FORM_JSP);
			mav = new ModelAndView(jspPath);
			String requestURL = getConfig().getRequestUrl().get(YeePayConstants.SERVICE_NAME_TOCPTRANSACTION);
			mav.addObject(YeePayConstants.PARAM_NAME_REQ, outboundXML);
			mav.addObject(YeePayConstants.PARAM_NAME_SIGN, sign);
			mav.addObject("requestURL", requestURL);
			String trackingNo = YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId());
			transaction.setTrackingNo(trackingNo);
			getPaymentService().updateTransaction(transaction);
			
			
			
		} catch (Exception e) {
			jspPath = getConfig().getServiceJspPath().get(YeePayConstants.SERVICE_NAME_TOCPTRANSACTION)
					.get(YeePayConstants.PARAM_NAME_ERROR_JSP);
			mav = new ModelAndView(jspPath);
			mav.addObject(YeePayConstants.PARAM_NAME_ERROR, e);
			getPaymentService().setAsRollback();
		} finally {
			getPaymentService().commit();
		}
		return mav;
	}

	@RequestMapping(value = "/completeTransactionNotify", method = RequestMethod.POST)
	public ResponseEntity handleCompleteTransactionNotify(HttpServletRequest pRequest, HttpServletResponse pResponse) {
		String inboundXML = pRequest.getParameter(YeePayConstants.PARAM_NAME_NOTIFY);
		String inboundSign = pRequest.getParameter(YeePayConstants.PARAM_NAME_SIGN);

		boolean pass = SignUtil.verifySign(inboundXML, inboundSign, "yeepay.com");
		if (!pass) {
			return new ResponseEntity(HttpStatus.OK);
		}

		Map<String, String> responseParams = YeePayHelper.parseXml(inboundXML);
		String requestNoStr = responseParams.get(YeePayConstants.PARAM_NAME_REQUEST_NO);
		int requestNo = YeePayHelper.parseRequestId(getConfig(), requestNoStr);
		TransactionLog transactionLog = getTransactionLogService().findByTrackingNo(requestNoStr);
		StringBuilder inboundBuilder = new StringBuilder();
		inboundBuilder.append("inboundXML: \n").append(inboundXML).append("\ninboudSign: \n").append(inboundSign);
		transactionLog.setInbound(inboundBuilder.toString());
		transactionLog.setInboundTime(new Date());
		getTransactionLogService().updateTransactionLog(transactionLog);

		if (null == getConfig().getNotificationHandler()) {
			LOGGER.error("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=notificationHandler");
			return new ResponseEntity(HttpStatus.OK);
		}

		String serviceName = transactionLog.getServiceName();
		if (StringUtils.isBlank(serviceName)) {
			LOGGER.error("serviceName is blank.");
			return new ResponseEntity(HttpStatus.OK);
		}

		// use service name to get handler from config. and call handler.
		YeepayNotificationHandler notificationHandler = getConfig().getNotificationHandler().get(serviceName);
		if (null == notificationHandler) {
			LOGGER.error("Please check yeepay-config.xml. bean: id=yeePayConfig, property: name=notificationHandler, key=" + serviceName);
			return new ResponseEntity(HttpStatus.OK);
		}

		Map<String, String> inboundParam = YeePayHelper.parseXml(inboundXML);
		getCompleteTransactionNotificationHandler().ensureTransaction();
		try {
			PaymentGroup paymentGroup = null;
			if (null == transactionLog.getPaymentGroupId()) {
				LOGGER.error("no paymentGroupId for transactionlog(id=" + transactionLog.getId() + ")");
				return new ResponseEntity(HttpStatus.OK);
			}
			paymentGroup = getPaymentService().findPaymentGroupById(transactionLog.getPaymentGroupId());
			if (null == paymentGroup) {
				LOGGER.error("no paymentGroup found(id=" + transactionLog.getPaymentGroupId() + ")");
				return new ResponseEntity(HttpStatus.OK);
			}

			Transaction transaction = null;
			transaction = getPaymentService().findTransactionByTrackingNumber(requestNoStr);
			if (null == transaction) {
				LOGGER.error("no transaction found(trackingNo=" + requestNoStr + ")");
				return new ResponseEntity(HttpStatus.OK);
			}
			Order order = null;
			if (null == transactionLog.getOrderId()) {
				LOGGER.error("no orderId for transactionLog(id=" + transactionLog.getId() + ")");
				return new ResponseEntity(HttpStatus.OK);
			}
			order = getCompleteTransactionNotificationHandler().getUserOrderDao().loadOrderHistory(transactionLog.getOrderId().intValue());
			if (null == order) {
				LOGGER.error("no order found(id=" + transactionLog.getOrderId() + ")");
				return new ResponseEntity(HttpStatus.OK);
			}
			getCompleteTransactionNotificationHandler().handleResult(paymentGroup, requestNoStr, inboundParam, transaction, new Date(), order);
			return new ResponseEntity(YeePayConstants.SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			getCompleteTransactionNotificationHandler().setAsRollback();
			return new ResponseEntity(HttpStatus.OK);
		} finally {
			getCompleteTransactionNotificationHandler().commit();
		}

	}

	private Map<String, Object> getPaymentParamMap(Order order, TransactionLog transactionLog,
			HttpServletRequest pRequest, HttpServletResponse pResponse) {

		Long userId = Long.valueOf(order.getUserId());
		String platformUserNo = YeePayHelper.generateOutboundUserNo(getConfig(), userId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(YeePayConstants.PARAM_NAME_REQUEST_NO,
				YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId()));
		paramMap.put(YeePayConstants.PARAM_NAME_PLATFORM_USER_NO, platformUserNo);
		paramMap.put(YeePayConstants.PARAM_NAME_USER_TYPE, YeePayConstants.USER_TYPE_MEMBER);
		paramMap.put(YeePayConstants.PARAM_NAME_BIZ_TYPE, YeePayConstants.BIZ_TYPE_TRANSFER);

		Map<String, Object> detailsMap = new HashMap<String, Object>();
		List<Map<String, Object>> detailsList = new ArrayList<>();
		paramMap.put(YeePayConstants.PARAM_NAME_DETAILS, detailsMap);
		detailsMap.put(YeePayConstants.PARAM_NAME_DETAILS, detailsList);
		Map<String, Object> detailMap = new HashMap<String, Object>();
		detailsList.add(detailMap);

		Map<String, Object> detail = new HashMap<String, Object>();
		detail.put(YeePayConstants.PARAM_NAME_BIZ_TYPE, YeePayConstants.BIZ_TYPE_TRANSFER);
		detail.put(YeePayConstants.PARAM_NAME_TARGET_USER_TYPE, YeePayConstants.USER_TYPE_MEMBER);
		detail.put(YeePayConstants.PARAM_NAME_TARGET_PLATFORM_USER_NO, getConfig().getTargetPlatformUserNo());
		detail.put(YeePayConstants.PARAM_NAME_AMOUNT, String.valueOf(order.getTotal()));
		detailMap.put(YeePayConstants.PARAM_NAME_DETAIL, detail);

		String callBackUrl = getConfig().getCallbackUrl() + "?servieName="
				+ YeePayConstants.SERVICE_NAME_TOCPTRANSACTION + "&orderId=" + order.getId() + ";jsessionid=" + pRequest.getSession().getId();
		paramMap.put(YeePayConstants.PARAM_NAME_CALLBACK_URL, callBackUrl);

		String notifyUrl = getConfig().getNotifyUrl();
		paramMap.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, notifyUrl);

		paramMap.put(YeePayConstants.PARAM_NAME_REMARK, "支付订单：" + order.getId());
		return paramMap;
	}

	private Map<String, Object> extractParams(HttpServletRequest pRequest, String serviceName,
			Map<String, String> paramConfig, TransactionLog transactionLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, String> entry : paramConfig.entrySet()) {
			if (YeePayConstants.PARAM_NAME_PLATFORM_NO.equals(entry.getKey())
					&& YeePayConstants.VALUE_REQUIRE.equals(entry.getValue())) {
				result.put(YeePayConstants.PARAM_NAME_PLATFORM_NO, getConfig().getPlatformNo());
			} else if (YeePayConstants.PARAM_NAME_FEE_MODE.equals(entry.getKey())
					&& YeePayConstants.VALUE_REQUIRE.equals(entry.getValue())) {
				result.put(YeePayConstants.PARAM_NAME_FEE_MODE, getConfig().getFeeMode().get(serviceName));
			} else if (YeePayConstants.PARAM_NAME_REQUEST_NO.equals(entry.getKey())) {
				continue;
			} else if (YeePayConstants.PARAM_NAME_CALLBACK_URL.equals(entry.getKey())) {
				continue;
			} else if (YeePayConstants.PARAM_NAME_NOTIFY_URL.equals(entry.getKey())) {
				continue;
			} else {
				String value = pRequest.getParameter(entry.getKey());
				if (YeePayConstants.VALUE_REQUIRE.equals(entry.getValue()) && StringUtils.isBlank(value)) {
					throw new IllegalArgumentException(entry.getKey());
				}
				result.put(entry.getKey(), value);
			}
		}

		if (YeePayConstants.VALUE_REQUIRE.equals(paramConfig.get(YeePayConstants.PARAM_NAME_CALLBACK_URL))) {
			String callBackUrl = getConfig().getCallbackUrl() + "?servieName=" + serviceName + ";jsessionid="
					+ pRequest.getSession().getId();
			result.put(YeePayConstants.PARAM_NAME_CALLBACK_URL, callBackUrl);
		}

		if (YeePayConstants.VALUE_REQUIRE.equals(paramConfig.get(YeePayConstants.PARAM_NAME_NOTIFY_URL))) {
			String notifyUrl = getConfig().getNotifyUrl();
			result.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, notifyUrl);
		}

		String userId = paramConfig.get(YeePayConstants.PARAM_NAME_USER_ID);
		String orderId = paramConfig.get(YeePayConstants.PARAM_NAME_ORDER_ID);
		String paymentGroupId = paramConfig.get(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID);
		String transactoinId = paramConfig.get(YeePayConstants.PARAM_NAME_TRANSACTION_ID);
		if (StringUtils.isNotBlank(userId)) {
			transactionLog.setUserId(Long.valueOf(userId));
		}
		if (StringUtils.isNotBlank(orderId)) {
			transactionLog.setOrderId(Long.valueOf(orderId));
		}
		if (StringUtils.isNotBlank(paymentGroupId)) {
			transactionLog.setPaymentGroupId(Long.valueOf(paymentGroupId));
		}
		if (StringUtils.isNotBlank(transactoinId)) {
			transactionLog.setTransactionId(Long.valueOf(transactoinId));
		}
		getTransactionLogService().createTransactionLog(transactionLog);

		if (YeePayConstants.VALUE_REQUIRE.equals(paramConfig.get(YeePayConstants.PARAM_NAME_REQUEST_NO))) {
			result.put(YeePayConstants.PARAM_NAME_REQUEST_NO,
					YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId()));
		}
		return result;
	}

	public YeePayConfig getConfig() {
		return config;
	}

	public void setConfig(YeePayConfig config) {
		this.config = config;
	}

	public TransactionLogService getTransactionLogService() {
		return transactionLogService;
	}

	public void setTransactionLogService(TransactionLogService transactionLogService) {
		this.transactionLogService = transactionLogService;
	}

	public URLConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}

	public void setUrlConfiguration(URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CompleteTransactionNotificationHandler getCompleteTransactionNotificationHandler() {
		return completeTransactionNotificationHandler;
	}

	public void setCompleteTransactionNotificationHandler(
			CompleteTransactionNotificationHandler completeTransactionNotificationHandler) {
		this.completeTransactionNotificationHandler = completeTransactionNotificationHandler;
	}

}
