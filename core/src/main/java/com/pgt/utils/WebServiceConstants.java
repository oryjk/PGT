package com.pgt.utils;

/**
 * Created by Administrator on 2016/1/23.
 */
public interface WebServiceConstants {

    int CODE_SUCCESS = 0;

    int CODE_NEED_LOGIN = 1000;
    int CODE_NOT_YOUR_ORDER = 2000;
    int CODE_TOO_MANY_ITEM = 2010;
    int CODE_BACK_TO_CART = 2011;
    int CODE_HAS_INCOMPLETE_ORDER = 2012;
    int CODE_NO_SHIPPING = 2013;
    int CODE_NO_INVENTORY = 2020;
    int CODE_CHECK_INVENTORY_FAILED = 2021;


    String NAME_CODE = "code";
    String NAME_ORDER_INFO = "orderInfo";
    String NAME_ADDRESS_LIST = "addressInfoList";
    String NAME_DEFAULT_ADDRESS =  "defaultAddress";
    String NAME_STORE_LIST = "storeList";
    String NAME_TRANSACTION_ID = "transactionId";

}
