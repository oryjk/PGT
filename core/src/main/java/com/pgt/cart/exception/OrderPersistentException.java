package com.pgt.cart.exception;

/**
 * Created by Yove on 10/28/2015.
 */
public class OrderPersistentException extends Exception {

	public OrderPersistentException() {
		super();
	}

	public OrderPersistentException(final String pMessage) {
		super(pMessage);
	}

	public OrderPersistentException(final Throwable pCause) {
		super(pCause);
	}

	public OrderPersistentException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}
}
