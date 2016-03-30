package com.pgt.cart.filter;

import com.pgt.cart.util.RepositoryUtils;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yove on 16/2/4.
 */
public abstract class ItemLoadInterceptor implements HandlerInterceptor {

	public static final String SUFFIX_CSS = ".css";
	public static final String SUFFIX_JPG = ".jpg";
	public static final String SUFFIX_JS  = ".js";
	public static final String SUFFIX_PNG = ".png";

	public static final String SUFFIX_ICO = ".ico";


	protected static final Logger LOGGER = LoggerFactory.getLogger(ItemLoadInterceptor.class);

	protected boolean mEnable = true;
	protected List<String> mInterceptURIs;
	protected List<String> mExcludeURISuffixes;
	protected String mRequestConstantName;

	@Autowired
	private URLConfiguration mURLConfiguration;

	@Override
	public boolean preHandle(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler)
			throws Exception {
		boolean loadResult = loadItems(pRequest, pResponse, pHandler);
		LOGGER.debug("Load items success: {}", loadResult);
		return true;
	}

	@Override
	public void postHandle(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler,
	                       final ModelAndView pModelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final Object pHandler,
	                            final Exception pEx) throws Exception {

	}

	public abstract boolean loadItems(HttpServletRequest pRequest, HttpServletResponse pResponse, Object pHandler);

	protected User getCurrentUser(HttpServletRequest pRequest) {
		return (User) pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
	}

	protected boolean checkLoginState(HttpServletRequest pRequest) {
		User user = getCurrentUser(pRequest);
		boolean login = user != null && RepositoryUtils.idIsValid(user.getId().intValue());
		LOGGER.debug("Checking login state and get login state: {}", login);
		return login;
	}

	protected boolean interceptRequest(HttpServletRequest pRequest) {
		String requestURI = pRequest.getRequestURI();
		LOGGER.debug("Current request URI: {}", requestURI);
		if (CollectionUtils.isNotEmpty(getExcludeURISuffixes())) {
			for (String excludeSuffix : getExcludeURISuffixes()) {
				if (excludeURI(requestURI, excludeSuffix)) {
					LOGGER.debug("Exclude current request URI for exclude URI suffix: {}", excludeSuffix);
					return false;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(getInterceptURIs())) {
			for (String interceptURIPath : getInterceptURIs()) {
				if (interceptURI(requestURI, interceptURIPath)) {
					LOGGER.debug("Intercept current request URI for intercept URI path: {}", requestURI);
					return true;
				}
			}
		}
		LOGGER.debug("Skip intercept request for no rules matching, current request URI: {}", requestURI);
		return false;
	}

	protected boolean excludeURI(String pRequestURI, String pURISuffix) {
		return pRequestURI.endsWith(pURISuffix);
	}

	protected boolean interceptURI(String pRequestURI, String pInterceptURI) {
		return pRequestURI.contains(pInterceptURI);
	}

	public boolean isEnable() {
		return mEnable;
	}

	public void setEnable(final boolean pEnable) {
		mEnable = pEnable;
	}

	public List<String> getInterceptURIs() {
		return mInterceptURIs;
	}

	public void setInterceptURIs(final List<String> pInterceptURIs) {
		mInterceptURIs = pInterceptURIs;
	}

	public String getRequestConstantName() {
		return mRequestConstantName;
	}

	public void setRequestConstantName(final String pRequestConstantName) {
		mRequestConstantName = pRequestConstantName;
	}

	public List<String> getExcludeURISuffixes() {
		return mExcludeURISuffixes;
	}

	public void setExcludeURISuffixes(final List<String> pExcludeURISuffixes) {
		mExcludeURISuffixes = pExcludeURISuffixes;
	}

	public URLConfiguration getURLConfiguration() {
		return mURLConfiguration;
	}

	public void setURLConfiguration(final URLConfiguration pURLConfiguration) {
		mURLConfiguration = pURLConfiguration;
	}
}