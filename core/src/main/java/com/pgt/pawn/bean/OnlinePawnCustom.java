package com.pgt.pawn.bean;

/**
 * Created by hd on 16-1-28.
 */
public class OnlinePawnCustom extends OnlinePawn {

	private OnlinePawn onlinePawn;

	private String smsCode;

	private String error;

	public OnlinePawn getOnlinePawn () {
		return onlinePawn;
	}

	public void setOnlinePawn (OnlinePawn onlinePawn) {
		this.onlinePawn = onlinePawn;
	}

	public String getSmsCode () {
		return smsCode;
	}

	public void setSmsCode (String smsCode) {
		this.smsCode = smsCode;
	}

	public String getError () {
		return error;
	}

	public void setError (String error) {
		this.error = error;
	}


}
