package com.pgt.cart.bean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;

public class ResponseBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBuilder.class);

	private MessageSource mMessageSource;

	private ResponseBean mResponseBean = new ResponseBean();

	public ResponseBuilder(MessageSource pMessageSource) {
		mMessageSource = pMessageSource;
	}

	public ResponseBean createResponse() {
		return mResponseBean;
	}

	public int getSuccess() {
		return mResponseBean.getSuccess();
	}

	public ResponseBuilder setSuccess(final int pSuccess) {
		mResponseBean.setSuccess(pSuccess);
		return this;
	}

	public Object getData() {
		return mResponseBean.getData();
	}

	public ResponseBuilder setData(final Object pData) {
		mResponseBean.setData(pData);
		return this;
	}

	public ResponseBuilder setSuccess(boolean pSuccess) {
		mResponseBean.setSuccess(pSuccess ? ResponseBean.SUCCESS : ResponseBean.NOT_SUCCESS);
		LOGGER.debug("Set success as: {}", pSuccess);
		return this;
	}

	public ResponseBuilder addMessage(String pProperty, String pKey) {
		return addMessage(pProperty, pKey, StringUtils.EMPTY);
	}

	public ResponseBuilder addMessage(String pProperty, String pKey, String pDefaultValue) {
		if (mResponseBean.getMessage() == null) {
			mResponseBean.setMessage(new HashMap<>());
		}
		String property = pProperty;
		if (StringUtils.isBlank(pProperty)) {
			property = ResponseBean.DEFAULT_PROPERTY;
		}
		String message = getMessageValue(pKey, pDefaultValue);
		mResponseBean.getMessage().put(property, message);
		LOGGER.debug("Add message to response: {} - {}", property, message);
		return this;
	}

	public ResponseBuilder addErrorMessage(String pProperty, String pKey) {
		return addErrorMessage(pProperty, pKey, StringUtils.EMPTY);
	}

	public ResponseBuilder addErrorMessage(String pProperty, String pKey, String pDefaultValue) {
		if (mResponseBean.getErrorMessage() == null) {
			mResponseBean.setErrorMessage(new HashMap<>());
		}
		String property = pProperty;
		if (StringUtils.isBlank(pProperty)) {
			property = ResponseBean.DEFAULT_PROPERTY;
		}
		String errorMessage = getMessageValue(pKey, pDefaultValue);
		mResponseBean.getErrorMessage().put(property, errorMessage);
		LOGGER.debug("Add error message to response: {} - {}", property, errorMessage);
		return this;
	}

	public ResponseBuilder setSystemMessage(String pSystemMessage) {
		mResponseBean.setSystemMessage(pSystemMessage);
		LOGGER.debug("Add system message to response: {}", pSystemMessage);
		return this;
	}

	protected String getMessageValue(String pKey, String pDefaultMessage) {
		if (StringUtils.isNotBlank(pKey)) {
			return mMessageSource.getMessage(pKey, null, pDefaultMessage, Locale.getDefault());
		} else {
			return StringUtils.EMPTY;
		}
	}

}