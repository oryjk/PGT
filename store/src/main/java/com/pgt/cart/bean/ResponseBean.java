package com.pgt.cart.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Yove on 11/30/2015.
 */
public class ResponseBean implements Serializable {

	public static final int SUCCESS = 1;
	public static final int NOT_SUCCESS = 0;

	public static final String DEFAULT_PROPERTY = "default";

	private int mSuccess = SUCCESS;
	private Object mData;
	private Map<String, String> mMessage;
	private Map<String, String> mErrorMessage;
	private String mSystemMessage;

	ResponseBean() {
	}

	public int getSuccess() {
		return mSuccess;
	}

	public void setSuccess(final int pSuccess) {
		mSuccess = pSuccess;
	}

	public Object getData() {
		return mData;
	}

	public void setData(final Object pData) {
		mData = pData;
	}

	public Map<String, String> getMessage() {
		return mMessage;
	}

	public void setMessage(final Map<String, String> pMessage) {
		mMessage = pMessage;
	}

	public Map<String, String> getErrorMessage() {
		return mErrorMessage;
	}

	public void setErrorMessage(final Map<String, String> pErrorMessage) {
		mErrorMessage = pErrorMessage;
	}

	public String getSystemMessage() {
		return mSystemMessage;
	}

	public void setSystemMessage(final String pSystemMessage) {
		mSystemMessage = pSystemMessage;
	}
}