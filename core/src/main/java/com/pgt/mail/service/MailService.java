package com.pgt.mail.service;

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

@Service
public class MailService {
	private static final Logger	LOGGER			= LoggerFactory.getLogger(MailService.class);
	@Autowired
	private VelocityEngine		velocityEngine;
	@Autowired
	private JavaMailSenderImpl	mailSender;
	private String				templatePath	= "";

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

}
