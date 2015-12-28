package com.pgt.utils;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class Transactionable {

	private ThreadLocal<Stack<TransactionStatus>> transactionStatusStack = new ThreadLocal<Stack<TransactionStatus>>();

	@Autowired
	private DataSourceTransactionManager transactionManager;

	public TransactionStatus ensureTransaction() {
		return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
	}

	public TransactionStatus ensureTransaction(int pPropagationBehavior) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(pPropagationBehavior);
		TransactionStatus status = getTransactionManager().getTransaction(def);
		getTransactionStatusStack().push(status);
		return status;
	}
	
	
	public TransactionStatus getCurrent() {
		TransactionStatus current = getTransactionStatusStack().peek();
		if (null == current) {
			throw new RuntimeException("No transaction.");
		}
		return current;
	}
	
	

	protected Stack<TransactionStatus> getTransactionStatusStack() {
		if (null == transactionStatusStack.get()) {
			transactionStatusStack.set(new Stack<TransactionStatus>());
		}
		return transactionStatusStack.get();
	}

	public void setAsRollback() {
		TransactionStatus current = getCurrent();
		current.setRollbackOnly();
	}

	public boolean isRollbackOnly() {
		TransactionStatus current = getCurrent();
		return current.isRollbackOnly();
	}

	
	public void commit() {
		TransactionStatus current = getTransactionStatusStack().pop();
		if (null == current) {
			throw new RuntimeException("No transaction.");
		}
		if (current.isCompleted()) {
			return;
		}
		getTransactionManager().commit(current);
		
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
