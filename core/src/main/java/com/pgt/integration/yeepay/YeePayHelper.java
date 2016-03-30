package com.pgt.integration.yeepay;

import com.pgt.user.bean.User;
import com.yeepay.g3.utils.security.cfca.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YeePayHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(YeePayHelper.class);
	public static String generateRequestXml(YeePayConfig config, Map<String, Object> params) {
		Document document = DocumentHelper.createDocument();
		Element request = document.addElement("request");
		request.addAttribute("platformNo", config.getPlatformNo());
		generateXml(request, params);
		return document.asXML().replaceAll(">\\s+<", "><");
	}

	public static String generateSign(YeePayConfig config, String requestXML) {

		return SignUtil.sign(requestXML, config.getPfxPath(), config.getPassword());
	}
	
	public static String generateSign(YeePayConfig config, String requestXML, String password) {
		return SignUtil.sign(requestXML, config.getPfxPath(), password);
	}

	public static String directCall(YeePayConfig config, String apiName, String requestXML, String requsetSign)
			throws IOException {
		if (null == config || null == config.getDirectRequestUrl() || StringUtils.isBlank(apiName)
				|| StringUtils.isBlank(requestXML) || StringUtils.isBlank(requsetSign)) {
			throw new InvalidParameterException();
		}

		String requestUrl = config.getDirectRequestUrl();

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(requestUrl);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(YeePayConstants.PARAM_NAME_SERVICE, apiName));
		nvps.add(new BasicNameValuePair(YeePayConstants.PARAM_NAME_REQ, requestXML));
		nvps.add(new BasicNameValuePair(YeePayConstants.PARAM_NAME_SIGN, requsetSign));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		CloseableHttpResponse response = httpclient.execute(httpPost);

		try {
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return responseContent;
		} finally {
			response.close();
		}
	}

	public static Map<String, String> parseXml(String xml) {

		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List<Element> subElements = root.elements();
			Map<String, String> result = new HashMap<>();
			for (Element sub : subElements) {
				String key = sub.getName();
				String value = sub.getText();
				result.put(key, value);
			}
			return result;
		} catch (DocumentException e) {
			LOGGER.error("parse yeepay response error", e);
		}

		return null;
	}

	public static void generateXml(Element element, Map<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() instanceof String) {
				Element subElement = element.addElement(entry.getKey());
				subElement.addText((String) entry.getValue());
			} else if (entry.getValue() instanceof Map) {
				Element subElement = element.addElement(entry.getKey());
				generateXml(subElement, (Map) entry.getValue());
			} else if (entry.getValue() instanceof List) {
				for (Map<String, Object> sub : (List<Map<String, Object>>) entry.getValue()) {
					generateXml(element, sub);
				}
			} else {
				continue;
			}
		}

	}

	public static Map<String, String> resolveNotify(YeePayConfig config, String sign, String NotifyXML) {
		return null;
	}

	public static void main(String[] args) throws IOException {
		 YeePayHelper helper = new YeePayHelper();
		 YeePayConfig config = new YeePayConfig();
		 config.setPlatformNo("pn1");
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 paramMap.put("requestNo", "1");
		 paramMap.put("userType", "MERCHENT");
		 paramMap.put("platformUserNo", "userNo");
		 Map<String, Object> detailsMap = new HashMap<String, Object>();
		 List<Map<String, Object>> detailsList = new ArrayList<>();
		 paramMap.put("details", detailsMap);
		 detailsMap.put("details", detailsList);
		 Map<String, Object> detailMap = new HashMap<String, Object>();
		 detailsList.add(detailMap);
		
		 Map<String, Object> detail = new HashMap<String, Object>();
		 detail.put("targetUserType", "type0");
		 detail.put("targetPlatformUserNo", "no0");
		 detail.put("amount", "0");
		 detailMap.put("detail", detail);
		
		
		 String xml = helper.generateRequestXml(config, paramMap);
		 System.out.println(xml);
		
		 String s = SignUtil.sign("hello",
		 "/projects/dianjinzi/cfca/output/hk1001001@test.com.p12.pfx",
		 "123qwe");
		 System.out.println(s);

		CloseableHttpClient httpclient = HttpClients.createDefault();


	}
	
	public static String generateOutboundUserNo(YeePayConfig config, User user) {
		if (StringUtils.isBlank(user.getYeepayUserNo())) {
			return config.getPlatformUserNoPrefix() + user.getId();
		}
		return user.getYeepayUserNo();
	}
	
	public static int parseUserId(YeePayConfig config, String platformUserNo) {
		String userId = platformUserNo.substring(config.getPlatformUserNoPrefix().length());
		return Integer.valueOf(userId);
		
	}
	
	public static String generateOutboundRequestNo(YeePayConfig config, Long requestNo) {
		return config.getRequestNoPrefix() + requestNo;
	}
	
	public static int parseRequestId(YeePayConfig config, String requestNo) {
		String requestNoStr = requestNo.substring(config.getRequestNoPrefix().length());
		return Integer.valueOf(requestNoStr);
		
	}
}
