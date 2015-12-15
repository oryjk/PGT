
package com.pgt.integration.yeepay;

public class YeePayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3801720994352586482L;
	
	public YeePayException() {
		super();
	}
	
	public YeePayException(String error) {
		super(error);
	}
	
	public YeePayException(Throwable throwable) {
		super(throwable);
	}
	
	
	public YeePayException(String error, Throwable throwable) {
		super(error);
	}
	
}
