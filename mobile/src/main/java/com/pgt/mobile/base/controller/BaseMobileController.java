package com.pgt.mobile.base.controller;

import com.pgt.mobile.base.constans.MobileConstans;

import java.util.Map;

/**
 * Created by xiaodong on 15-12-24.
 */
public class BaseMobileController {


    protected Map<String, Object> responseMobileFail(Map<String, Object> responseMap, String value) {
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
        responseMap.put(MobileConstans.MOBILE_MESSAGE, value);
        return responseMap;
    }


}
