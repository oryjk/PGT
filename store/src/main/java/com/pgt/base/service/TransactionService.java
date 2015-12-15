package com.pgt.base.service;

import com.pgt.internal.constant.SessionConstant;
import com.pgt.internal.controller.InternalUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by carlwang on 11/1/15.
 */
public abstract class TransactionService {
    @Autowired
    private DataSourceTransactionManager transactionManager;

    protected TransactionStatus ensureTransaction() {
        return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected TransactionStatus ensureTransaction(int propagationBehavior) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(propagationBehavior);
        TransactionStatus status = getTransactionManager().getTransaction(def);
        return status;
    }


    protected InternalUserController getCurrentInternalUser(HttpServletRequest pRequest) {
        return (InternalUserController) pRequest.getSession().getAttribute(SessionConstant.INTERNAL_USER);
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
