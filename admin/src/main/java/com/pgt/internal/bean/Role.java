package com.pgt.internal.bean;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Yove on 10/19/2015.
 */
public enum Role {

	GHOST(-1), BROWSER(0), MERCHANDISER(1), INVESTOR(2), ORDER_MANAGER(3), ADMINISTRATOR(4);

	private int mValue;

	private static final String TEXT_GHOST = "什么鬼!";
	private static final String TEXT_BROWSER = "记录管理员";
	private static final String TEXT_MERCHANDISER = "商品管理员";
	private static final String TEXT_INVESTOR = "投资管理员";
	private static final String TEXT_ORDER_MANAGER = "订单管理员";
	private static final String TEXT_ADMINISTRATOR = "后台管理员";

	private static Map<Role, String> ROLE_NAME_MAP;

	public static String[] ROLE_NAMES = new String[] {TEXT_BROWSER, TEXT_MERCHANDISER, TEXT_INVESTOR, TEXT_ORDER_MANAGER, TEXT_ADMINISTRATOR};
	public static Role[] ROLES = new Role[] {BROWSER, MERCHANDISER, INVESTOR, ORDER_MANAGER, ADMINISTRATOR};

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
			case BROWSER:
				return TEXT_BROWSER;
			case MERCHANDISER:
				return TEXT_MERCHANDISER;
			case INVESTOR:
				return TEXT_INVESTOR;
			case ORDER_MANAGER:
				return TEXT_ORDER_MANAGER;
			case ADMINISTRATOR:
				return TEXT_ADMINISTRATOR;
			default:
				// this is incorrect logic
				return TEXT_GHOST;
		}
	}

	public static Role valueOf(int pValue) {
		switch (pValue) {
			case 0:
				return BROWSER;
			case 1:
				return MERCHANDISER;
			case 2:
				return INVESTOR;
			case 3:
				return ORDER_MANAGER;
			case 4:
				return ADMINISTRATOR;
			default:
				// this is incorrect logic
				return GHOST;
		}
	}

	public static Map<Role, String> getRoleNameMap() {
		if (ROLE_NAME_MAP == null) {
			ROLE_NAME_MAP = new TreeMap<>();
			ROLE_NAME_MAP.put(BROWSER, TEXT_BROWSER);
			ROLE_NAME_MAP.put(MERCHANDISER, TEXT_MERCHANDISER);
			ROLE_NAME_MAP.put(INVESTOR, TEXT_INVESTOR);
			ROLE_NAME_MAP.put(ORDER_MANAGER, TEXT_ORDER_MANAGER);
			ROLE_NAME_MAP.put(ADMINISTRATOR, TEXT_ADMINISTRATOR);
		}
		return ROLE_NAME_MAP;
	}
}
