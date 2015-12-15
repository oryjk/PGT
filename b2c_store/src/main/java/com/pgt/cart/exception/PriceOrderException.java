package com.pgt.cart.exception;

/**
 * Created by Yove on 10/29/2015.
 */
public class PriceOrderException extends Exception {

	public PriceOrderException() {
		super();
	}

	public PriceOrderException(final String pMessage) {
		super(pMessage);
	}

	public PriceOrderException(final Throwable pCause) {
		super(pCause);
	}

	public PriceOrderException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}
}
