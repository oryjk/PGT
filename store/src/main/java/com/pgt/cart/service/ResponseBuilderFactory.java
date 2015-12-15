package com.pgt.cart.service;

import com.pgt.cart.bean.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

/**
 * Created by Yove on 11/30/2015.
 */
@Service(value = "responseBuilderFactory")
public class ResponseBuilderFactory {

	@Autowired
	private ReloadableResourceBundleMessageSource mMessageSource;

	public ResponseBuilder buildResponseBean() {
		return new ResponseBuilder(mMessageSource);
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return mMessageSource;
	}

	public void setMessageSource(final ReloadableResourceBundleMessageSource pMessageSource) {
		mMessageSource = pMessageSource;
	}
}
