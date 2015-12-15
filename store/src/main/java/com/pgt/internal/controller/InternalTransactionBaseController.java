package com.pgt.internal.controller;

import com.pgt.internal.constant.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yove on 10/20/2015.
 */
public abstract class InternalTransactionBaseController {

	@Autowired
	private DataSourceTransactionManager mTransactionManager;

	protected TransactionStatus ensureTransaction() {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		return status;
	}


	protected InternalUserController getCurrentInternalUser(HttpServletRequest pRequest) {
		return (InternalUserController) pRequest.getSession().getAttribute(SessionConstant.INTERNAL_USER);
	}

	public DataSourceTransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
		mTransactionManager = pTransactionManager;
	}
}
