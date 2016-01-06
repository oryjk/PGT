package com.pgt.executor.processor;

import com.pgt.executor.IInitializationHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Date;

/**
 * Created by Yove on 5/1/2015.
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(InstantiationTracingBeanPostProcessor.class);

	private IInitializationHandler[] mInitializationHandlers;

	public static final String FMC_ROOT_WEB_CURRENT_WEBAPPLICATIONCONTEXT = "FMC.ROOT.WEB.CurrentWebApplicationContext";

	/**
	 * @see ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent pEvent) {
		//Web context initialized.
		LOG.debug("===================== WebApplicationContext Completed ===============");

		ApplicationContext applicationContext = pEvent.getApplicationContext();
		if (applicationContext instanceof WebApplicationContext) {
			// get the parent context
			WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
			ServletContext servletContext = webApplicationContext.getServletContext();
			servletContext.setAttribute("launchDate", new Date());
			servletContext.setAttribute(FMC_ROOT_WEB_CURRENT_WEBAPPLICATIONCONTEXT, webApplicationContext);

			LOG.debug("===================== Application Properties Load Start ===============");
			invokeInitializationHandlers(webApplicationContext);
			LOG.debug("===================== Application Properties Load End ===============");
		}
	}

	protected void invokeInitializationHandlers(WebApplicationContext pWebApplicationContext) {
		if (ArrayUtils.isEmpty(mInitializationHandlers)) {
			return;
		}
		for (IInitializationHandler handler : mInitializationHandlers) {
			LOG.debug("Start to load init-handler: " + handler.getClass());
			handler.initialize(pWebApplicationContext);
			LOG.debug("End to load init-handler: " + handler.getClass());
		}
	}

	public void setInitializationHandlers(final IInitializationHandler[] pInitializationHandlers) {
		mInitializationHandlers = pInitializationHandlers;
	}
}
