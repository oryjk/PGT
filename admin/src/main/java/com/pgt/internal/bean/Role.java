package com.pgt.internal.bean;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Yove on 10/19/2015.
 */
public enum Role {

	GHOST(-1), BROWSER(0), MERCHANDISER(1), INVESTOR(2), PROD_ORDER_MANAGER(3), IVST_ORDER_MANAGER(4), ADMINISTRATOR(9);

	private static final String   TEXT_GHOST              = "什么鬼!";
	private static final String   TEXT_BROWSER            = "审阅管理员";
	private static final String   TEXT_MERCHANDISER       = "商品管理员";
	private static final String   TEXT_INVESTOR           = "投资管理员";
	private static final String   TEXT_PROD_ORDER_MANAGER = "商品订单管理员";
	private static final String   TEXT_IVST_ORDER_MANAGER = "投资订单管理员";
	private static final String   TEXT_ADMINISTRATOR      = "后台管理员";
	public static        String[] ROLE_NAMES              = new String[] { TEXT_BROWSER, TEXT_MERCHANDISER, TEXT_INVESTOR, TEXT_PROD_ORDER_MANAGER, TEXT_IVST_ORDER_MANAGER, TEXT_ADMINISTRATOR };
	public static        Role[]   ROLES                   = new Role[] { BROWSER, MERCHANDISER, INVESTOR, PROD_ORDER_MANAGER, IVST_ORDER_MANAGER, ADMINISTRATOR };
	private static Map<Role, String> ROLE_NAME_MAP;
	private        int               mValue;

	Role (int pValue) {
		mValue = pValue;
	}

	public static Role valueOf (int pValue) {
		switch (pValue) {
			case 0:
				return BROWSER;
			case 1:
				return MERCHANDISER;
			case 2:
				return INVESTOR;
			case 3:
				return PROD_ORDER_MANAGER;
			case 4:
				return IVST_ORDER_MANAGER;
			case 9:
				return ADMINISTRATOR;
			default:
				// this is incorrect logic
				return GHOST;
		}
	}

	public static Map<Role, String> getRoleNameMap () {
		if (ROLE_NAME_MAP == null) {
			ROLE_NAME_MAP = new TreeMap<>();
			ROLE_NAME_MAP.put(BROWSER, TEXT_BROWSER);
			ROLE_NAME_MAP.put(MERCHANDISER, TEXT_MERCHANDISER);
			ROLE_NAME_MAP.put(INVESTOR, TEXT_INVESTOR);
			ROLE_NAME_MAP.put(PROD_ORDER_MANAGER, TEXT_PROD_ORDER_MANAGER);
			ROLE_NAME_MAP.put(IVST_ORDER_MANAGER, TEXT_IVST_ORDER_MANAGER);
			ROLE_NAME_MAP.put(ADMINISTRATOR, TEXT_ADMINISTRATOR);
		}
		return ROLE_NAME_MAP;
	}

	public int getValue () {
		return mValue;
	}

	public String getName () {
		return this.name();
	}

	@Override
	public String toString () {
		switch (this) {
			case BROWSER:
				return TEXT_BROWSER;
			case MERCHANDISER:
				return TEXT_MERCHANDISER;
			case INVESTOR:
				return TEXT_INVESTOR;
			case PROD_ORDER_MANAGER:
				return TEXT_PROD_ORDER_MANAGER;
			case IVST_ORDER_MANAGER:
				return TEXT_IVST_ORDER_MANAGER;
			case ADMINISTRATOR:
				return TEXT_ADMINISTRATOR;
			default:
				// this is incorrect logic
				return TEXT_GHOST;
		}
	}
}
