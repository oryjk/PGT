package com.pgt.mail.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Delivery;
import com.pgt.cart.bean.Order;
import com.pgt.mail.MailConstants;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;

@Service
public class MailService {
	private static final Logger		LOGGER			= LoggerFactory.getLogger(MailService.class);
	@Autowired
	private VelocityEngine			velocityEngine;
	@Autowired
	private JavaMailSenderImpl		mailSender;
	@Autowired
	private UserInformationService	userInformationService;
	private String					templatePath	= "";

	public boolean sendEmail(String subject, Map<String, Object> params, String templateName, String... receivers) {
		if (ArrayUtils.isEmpty(receivers)) {
			LOGGER.error("The email receivers should not be empty.");
			return false;
		}
		if (StringUtils.isBlank(templateName)) {
			LOGGER.error("The email template name should not be empty.");
			return false;
		}
		String text = StringUtils.EMPTY;
		try {
			text = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), templateName, "utf-8", params);
		} catch (VelocityException e) {
			LOGGER.error("Failed to generate email template.", e);
		}
		MimeMessage mimeMessage = getMailSender().createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			helper.setSubject(subject);
			helper.setFrom(getMailSender().getUsername());
			helper.setTo(receivers);
			helper.setText(text, true);
		} catch (MessagingException e) {
			LOGGER.error("Failed to create mail message.", e);
			return false;
		}
		getMailSender().send(mimeMessage);
		return true;
	}

	public boolean sendEmail(String subject, Map<String, Object> params, String templateName, Order order) {
		try {
			if (order == null) {
				return false;
			}
			int userId = order.getUserId();
			User user = new User();
			user.setId(Long.valueOf(userId));
			UserInformation userInformation = userInformationService.queryUserInformation(user);
			if (userInformation == null || userInformation.getPersonEmail() == null) {
				LOGGER.info(
						"Current user has not add user information so that cannot send email to he/she. User id is:{}, order id is:{}.",
						user.getId(), order.getId());
				return false;
			}
			Map<String, Object> map = params;
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			map.put("user", userInformation.getUser());
			map.put("order", order);
			return sendEmail(subject, map, templateName, userInformation.getPersonEmail());
		} catch (Exception e) {
			LOGGER.error("Method sendEmail: Failed to create mail message.", e);
			return false;
		}
	}

	public void sendUpdateOrderEmail(Order order, boolean result, int statusInt) {
		if (!result) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("updateStatusInt", statusInt);
		sendEmail(MailConstants.SUBJECT_ORDER_CHANGED, params, MailConstants.TEMPLATE_ORDER_CHANGED, order);
	}

	public void sendDeliveryEmail(Order order, CommerceItem ci, Delivery delivery) {
		if (ci == null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commerceItem", ci);
		params.put("delivery", delivery);
		sendEmail(MailConstants.SUBJECT_SHIPPED_NOTIFY, params, MailConstants.TEMPLATE_SHIPPED_NOTIFY, order);
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public UserInformationService getUserInformationService() {
		return userInformationService;
	}

	public void setUserInformationService(UserInformationService userInformationService) {
		this.userInformationService = userInformationService;
	}

}
