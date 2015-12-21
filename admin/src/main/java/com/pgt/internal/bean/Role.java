package com.pgt.internal.bean;

/**
 * Created by Yove on 10/19/2015.
 */
public enum Role {

	GHOST(-1), INVESTOR(1), ADMINISTRATOR(2), DEVELOPER(4);

	private int mValue;

	private static final String TEXT_INVESTOR = "投资管理员";
	private static final String TEXT_ADMINISTRATOR = "后台管理员";
	private static final String TEXT_DEVELOPER = "开发管理员";
	private static final String TEXT_GHOST = "什么鬼!";

	Role(int pValue) {
		mValue = pValue;
	}

	public int getValue() {
		return mValue;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		switch (this) {
			case INVESTOR:
				return TEXT_INVESTOR;
			case ADMINISTRATOR:
				return TEXT_ADMINISTRATOR;
			case DEVELOPER:
				return TEXT_DEVELOPER;
			default:
				// this is incorrect logic
				return TEXT_GHOST;
		}
	}

	public static Role valueOf(int pValue) {
		switch (pValue) {
			case 1:
				return INVESTOR;
			case 2:
				return ADMINISTRATOR;
			case 3:
				return DEVELOPER;
			default:
				// this is incorrect logic
				return GHOST;
		}
	}
}
