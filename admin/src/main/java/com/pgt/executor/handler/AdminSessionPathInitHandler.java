package com.pgt.executor.handler;

import com.pgt.executor.IInitializationHandler;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Yove on 1/6/2016.
 */
public class AdminSessionPathInitHandler implements IInitializationHandler {

	@Override
	public void initialize(final WebApplicationContext pWebApplicationContext) {
		// Only support in servlet 3.0
		// ServletContext servletContext = pWebApplicationContext.getServletContext();
		// SessionCookieConfig sessionCookie = servletContext.getSessionCookieConfig();
		// sessionCookie.setPath("/");
	}
}
