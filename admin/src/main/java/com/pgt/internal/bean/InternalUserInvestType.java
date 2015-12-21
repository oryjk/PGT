package com.pgt.internal.bean;

/**
 * Created by Yove on 10/22/2015.
 */
public enum InternalUserInvestType {

	NONEED(-1), INDIVIDUAL(1), ENTERPRISE(2);

	private int mValue;

	private static final String TEXT_INDIVIDUAL = "个人投资";
	private static final String TEXT_ENTERPRISE = "企业投资";
	private static final String TEXT_NONEED = "什么投资都不管";

	InternalUserInvestType(int pValue) {
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
			case INDIVIDUAL:
				return TEXT_INDIVIDUAL;
			case ENTERPRISE:
				return TEXT_ENTERPRISE;
			default:
				// for administrator or developer
				return TEXT_NONEED;
		}
	}

	public static InternalUserInvestType valueOf(int pValue) {
		switch (pValue) {
			case 1:
				return INDIVIDUAL;
			case 2:
				return ENTERPRISE;
			default:
				// for administrator or developer
				return NONEED;
		}
	}
}
