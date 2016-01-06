package com.pgt.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Yove on 5/24/2015.
 */
public interface IInitializationHandler {

	/**
	 * Initialize method to deal with business
	 *
	 * @param pWebApplicationContext
	 */
	void initialize(WebApplicationContext pWebApplicationContext);
}
