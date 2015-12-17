package com.pgt.integration.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.constant.UserConstant;
import com.pgt.integration.alipay.bean.AlipayResult;
import com.pgt.integration.alipay.dao.AlipayResultMapper;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.user.bean.User;

@Service
public class AlipayService {
	private static final Logger		LOGGER	= LoggerFactory.getLogger(AlipayService.class);
	@Autowired
	private AlipayConfig			alipayConfig;
	@Autowired
	private AlipayResultMapper		alipayResultMapper;
	@Autowired
	private TransactionLogService	transactionLogService;

	public Map<String, String> buildRequestMap(Order order) {
		Map<String, String> paramMap = buildParamMap(order);
		paramMap.put(AlipayConstants.SIGN, sign(paramMap, alipayConfig.getKey(), alipayConfig.getInputCharset()));
		paramMap.put(AlipayConstants.SIGN_TYPE, alipayConfig.getSignType());
		return paramMap;
	};

	public Map<String, String> buildParamMap(Order order) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put(AlipayConstants.INPUT_CHARSET, alipayConfig.getInputCharset());
		paramMap.put(AlipayConstants.OUT_TRADE_NO, String.valueOf(order.getId()));
		paramMap.put(AlipayConstants.PARTNER, alipayConfig.getPartner());
		paramMap.put(AlipayConstants.PAYMENT_TYPE, alipayConfig.getPaymentType());
		paramMap.put(AlipayConstants.RETURN_URL, alipayConfig.getReturnUrl());
		paramMap.put(AlipayConstants.NOTIFY_URL, alipayConfig.getNotifyUrl());
		paramMap.put(AlipayConstants.SELLER_ID, alipayConfig.getPartner());
		paramMap.put(AlipayConstants.SERVICE, alipayConfig.getService());
		paramMap.put(AlipayConstants.SUBJECT, "绝当品编号:" + order.getId());
		paramMap.put(AlipayConstants.TOTAL_FEE, String.valueOf(order.getTotal()));
		return paramMap;
	}

	public static String sign(Map<String, String> map, String key, String charSet) {
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			content.append(keys.get(i));
			content.append("=");
			content.append(map.get(keys.get(i)));
			if (i != keys.size() - 1) {
				content.append("&");
			}
		}
		return MD5.sign(content.toString(), key, charSet); // MD5加密
	}

	public boolean verifyResult(HttpServletRequest request) {
		Map<String, String[]> requestParams = request.getParameterMap();
		List<String> keys = new ArrayList<String>(requestParams.keySet());
		Collections.sort(keys);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if (!key.equals("") && !key.equals(AlipayConstants.SIGN) && !key.equals(AlipayConstants.SIGN_TYPE)) {
				content.append(keys.get(i));
				content.append("=");
				content.append(requestParams.get(keys.get(i))[0]);
				if (i != keys.size() - 1) {
					content.append("&");
				}
			}
		}
		String mysign = MD5.sign(content.toString(), alipayConfig.getKey(), alipayConfig.getInputCharset());
		String orderId = request.getParameter(AlipayConstants.OUT_TRADE_NO);
		if (!mysign.equals(request.getParameter(AlipayConstants.SIGN))) {
			LOGGER.error("Trade_Failed: The parameter sign is not equals signed parameters reslut. Order id is{}",
					orderId);
			return false;
		}
		String alipayNotifyURL = alipayConfig.getAlipayUrl() + "?service=notify_verify&partner="
				+ alipayConfig.getPartner() + "&notify_id=" + request.getParameter("notify_id");
		String responseTxt = check(alipayNotifyURL);
		if (!"true".equals(responseTxt)) {
			LOGGER.error("Trade_Failed: The current notify is invalid caused by:{}. Order id is{}", responseTxt,
					orderId);
			return false;
		}
		String tradeStatus = request.getParameter(AlipayConstants.TRADE_STATUS);
		if (!AlipayConstants.TRADE_FINISHED.equalsIgnoreCase(tradeStatus)
				&& !AlipayConstants.TRADE_SUCCESS.equalsIgnoreCase(tradeStatus)) {
			LOGGER.error(
					"Trade_Failed: The trade status is not success, the value of parameter 'trade_status' is:{}, Order id is{}",
					tradeStatus, orderId);
			return false;
		}
		return true;
	}

	public static String check(String urlvalue) {
		StringBuilder inputLine = new StringBuilder();
		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String stTemp = "";
			while ((stTemp = in.readLine()) != null) {
				inputLine = inputLine.append(stTemp);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to check alipay result status.", e);
		}
		return inputLine.toString();
	}

	public void createAlipayTransactionLog(HttpSession session, PaymentGroup paymentGroup,
			Map<String, String> paramsMap) {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setPaymentType(AlipayConstants.TYPE);
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user != null) {
			transactionLog.setUserId(user.getId());
		}
		Order order = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
		if (order != null) {
			transactionLog.setOrderId((long) order.getId());
			transactionLog.setTransactionId((long) order.getId());
		}
		if (paymentGroup != null) {
			transactionLog.setPaymentGroupId(paymentGroup.getId());
		}
		transactionLog.setOutbound(paramsMap.toString());
		transactionLog.setOutboundTime(new Date());
		getTransactionLogService().createTransactionLog(transactionLog);
	}

	public void updateAlipayTransactionLog(HttpServletRequest request) {
		TransactionLog transactionLog = new TransactionLog();
		Map<String, String[]> requestParams = request.getParameterMap();
		transactionLog.setInbound(requestParams.toString());
		transactionLog.setOutboundTime(new Date());
		getTransactionLogService().updateTransactionLog(transactionLog);
	}

	public void saveAlipayResult(HttpServletRequest request) {
		AlipayResult alipayResult = new AlipayResult();
		try {
			alipayResult.setOrderId(Integer.parseInt(request.getParameter(AlipayConstants.OUT_TRADE_NO)));
			// alipayResult.setOrderId(0);
			alipayResult.setBuyerId(request.getParameter(AlipayConstants.BUYER_ID));
			alipayResult.setBuyerAccount(request.getParameter(AlipayConstants.BUYER_ACCOUNT));
			alipayResult.setCreationDate(new Date());
			alipayResult.setUpdateDate(new Date());
		} catch (Exception e) {
			LOGGER.error("Failed to save alipay result for order:{}",
					request.getParameter(AlipayConstants.OUT_TRADE_NO));
		}
		alipayResultMapper.createAlipayResult(alipayResult);
	}

	public AlipayConfig getAlipayConfig() {
		return alipayConfig;
	}

	public void setAlipayConfig(AlipayConfig alipayConfig) {
		this.alipayConfig = alipayConfig;
	}

	public TransactionLogService getTransactionLogService() {
		return transactionLogService;
	}

	public void setTransactionLogService(TransactionLogService transactionLogService) {
		this.transactionLogService = transactionLogService;
	}

}
