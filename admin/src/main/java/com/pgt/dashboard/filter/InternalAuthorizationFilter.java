package com.pgt.dashboard.filter;


import com.pgt.cart.constant.CookieConstant;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.constant.AdminSessionConstant;
import com.pgt.internal.service.InternalUserService;
import com.pgt.utils.CookieUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Yove on 1/6/2016.
 */
public class InternalAuthorizationFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternalAuthorizationFilter.class);

	private InternalUserService mInternalUserService;

	private boolean mEnable;

	private static final String SUFFIX_JS = ".js";
	private static final String SUFFIX_CSS = ".css";
	private static final String SUFFIX_JPG = ".jpg";
	private static final String SUFFIX_PNG = ".png";

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		String enableValue = filterConfig.getInitParameter("enable");
		mEnable = Boolean.valueOf(enableValue);
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
		mInternalUserService = ctx.getBean(InternalUserService.class);
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		if (!isEnable() || checkLoginStatus(request)) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("/login").forward(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	protected boolean checkLoginStatus(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String requestURI = httpRequest.getRequestURI();
			if (requestURI.contains("/login")) {
				LOGGER.debug("Pass login page request.");
				return true;
			}
			if (requestURI.endsWith(SUFFIX_JS) || requestURI.endsWith(SUFFIX_CSS) || requestURI.endsWith(SUFFIX_JPG) || requestURI.endsWith(SUFFIX_PNG)) {
				LOGGER.debug("Pass js/css/image request.");
				return true;
			}
			InternalUser iu = (InternalUser) httpRequest.getSession().getAttribute(AdminSessionConstant.INTERNAL_USER);
			if (iu != null && RepositoryUtils.idIsValid(iu.getId())) {
				LOGGER.debug("Pass request with signed in state.");
				return true;
			}
			LOGGER.debug("No valid session internal user found.");
			// hasn't not login, check cookie login
			Cookie[] cookies = httpRequest.getCookies();
			String token = null;
			if (ArrayUtils.isNotEmpty(cookies)) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (StringUtils.equals(CookieConstant.INTERNAL_LOGIN_TOKEN, cookie.getName())) {
						token = cookie.getValue();
						break;
					}
				}
			}
			if (StringUtils.isBlank(token)) {
				LOGGER.debug("Redirect request with invalid session internal user & empty cookie.");
				return false;
			}
			token = CookieUtils.decodeBase64(token);
			if (StringUtils.isBlank(token)) {
				LOGGER.debug("Redirect request with invalid session internal user & invalid cookie.");
				return false;
			}
			Object[] values = token.split(InternalUserService.SPLIT);
			if (values == null || values.length != 2) {
				LOGGER.debug("Redirect request with invalid session internal user & invalid cookie.");
				return false;
			}
			long expiration = RepositoryUtils.safeParse2LongId(String.valueOf(values[1]));
			if (expiration < System.currentTimeMillis()) {
				LOGGER.debug("Redirect request with invalid session internal user & out of date cookie.");
				return false;
			}
			int uid = RepositoryUtils.safeParseId(String.valueOf(values[0]));
			InternalUser currentUser = getInternalUserService().findInternalUser(uid);
			if (currentUser == null) {
				LOGGER.debug("Redirect request for none internal user found with id: " + values[0]);
				return false;
			}
			httpRequest.getSession().setAttribute(AdminSessionConstant.INTERNAL_USER, currentUser);
		}
		return true;
	}

	public InternalUserService getInternalUserService() {
		return mInternalUserService;
	}

	public void setInternalUserService(final InternalUserService pInternalUserService) {
		mInternalUserService = pInternalUserService;
	}

	public boolean isEnable() {
		return mEnable;
	}

	public void setEnable(final boolean pEnable) {
		mEnable = pEnable;
	}
}
