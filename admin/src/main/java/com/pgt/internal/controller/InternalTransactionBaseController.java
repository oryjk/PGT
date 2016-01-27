package com.pgt.internal.controller;

import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.bean.Role;
import com.pgt.internal.constant.AdminSessionConstant;
import com.pgt.internal.service.RolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Yove on 10/20/2015.
 */
public abstract class InternalTransactionBaseController implements AdminSessionConstant {

	protected static final String REDIRECT_DASHBOARD = "redirect:/";
	protected static final String REDIRECT_LOGIN     = "redirect:/login";
	protected static final String PERMISSION_DENIED  = "/permission_denied";
	private static final   Logger LOGGER             = LoggerFactory.getLogger(InternalTransactionBaseController.class);

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Autowired
	private ReloadableResourceBundleMessageSource mMessageSource;

	@Autowired
	private RolePermissionService mRolePermissionService;

	protected TransactionStatus ensureTransaction () {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction (int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		return status;
	}

	protected String getMessageValue (String pKey) {
		return getMessageValue(pKey, StringUtils.EMPTY);
	}

	protected String getMessageValue (String pKey, String pDefaultMessage) {
		if (StringUtils.isNotBlank(pKey)) {
			return mMessageSource.getMessage(pKey, null, pDefaultMessage, Locale.getDefault());
		} else {
			return StringUtils.EMPTY;
		}
	}

	protected InternalUser getCurrentInternalUser (HttpServletRequest pRequest) {
		return (InternalUser) pRequest.getSession().getAttribute(INTERNAL_USER);
	}

	protected boolean verifyPermission (HttpServletRequest pRequest, Role... pRoles) {
		InternalUser iu = getCurrentInternalUser(pRequest);
		if (iu != null) {
			boolean verified = getRolePermissionService().checkRole(iu.getRole(), pRoles);
			if (!verified) {
				LOGGER.debug("Permission: {} verify failed for current permission: {} of user: {} with request uri: {}", Arrays.toString(pRoles), iu.getRole(), iu.getId(), pRequest.getRequestURI());
			}
			return verified;
		}
		LOGGER.warn("Cannot find internal user from session but try to request uri: {}", pRequest.getRequestURI());
		return true;
	}

	protected boolean verifyPermission (HttpServletRequest pRequest) {
		InternalUser iu = getCurrentInternalUser(pRequest);
		if (iu != null) {
			boolean verified = iu.getRole().getValue() > Role.BROWSER.getValue();
			if (!verified) {
				LOGGER.debug("Permission: {} verify failed for current permission: {} of user: {}", Role.BROWSER, iu.getRole(), iu.getId());
			}
			return verified;
		}
		LOGGER.warn("Cannot find internal user from session!");
		return true;
	}

	public DataSourceTransactionManager getTransactionManager () {
		return mTransactionManager;
	}

	public void setTransactionManager (final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public ReloadableResourceBundleMessageSource getMessageSource () {
		return mMessageSource;
	}

	public void setMessageSource (final ReloadableResourceBundleMessageSource pMessageSource) {
		mMessageSource = pMessageSource;
	}

	public RolePermissionService getRolePermissionService () {
		return mRolePermissionService;
	}

	public void setRolePermissionService (final RolePermissionService pRolePermissionService) {
		mRolePermissionService = pRolePermissionService;
	}
}
