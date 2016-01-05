package com.pgt.internal.controller;

import com.pgt.internal.constant.AdminSessionConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Yove on 10/20/2015.
 */
public abstract class InternalTransactionBaseController implements AdminSessionConstant {

	protected static final String REDIRECT_DASHBOARD = "redirect:/dashboard";
	protected static final String REDIRECT_LOGIN = "redirect:/login";

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	@Autowired
	private ReloadableResourceBundleMessageSource mMessageSource;

	protected TransactionStatus ensureTransaction() {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		return status;
	}

	protected String getMessageValue(String pKey) {
		return getMessageValue(pKey, StringUtils.EMPTY);
	}

	protected String getMessageValue(String pKey, String pDefaultMessage) {
		if (StringUtils.isNotBlank(pKey)) {
			return mMessageSource.getMessage(pKey, null, pDefaultMessage, Locale.getDefault());
		} else {
			return StringUtils.EMPTY;
		}
	}

	protected InternalUserController getCurrentInternalUser(HttpServletRequest pRequest) {
		return (InternalUserController) pRequest.getSession().getAttribute(INTERNAL_USER);
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return mMessageSource;
	}

	public void setMessageSource(final ReloadableResourceBundleMessageSource pMessageSource) {
		mMessageSource = pMessageSource;
	}
}
