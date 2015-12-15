package com.pgt.utils;

import com.pgt.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by carlwang on 11/19/15.
 */
@Component
public class URLMapping {


    public String getHomePage() {
        return Constants.HOME_PAGE;
    }

    public String getLoginPage() {
        return Constants.LOGIN_PAGE;
    }

    public String getTRegisterPage() {
        return Constants.REGISTER_PAGE;
    }

    public String getPDPUrl(String productId) {
        if (!StringUtils.isBlank(productId)) {

            return Constants.PDP_URL + productId;
        }
        return null;

    }
}
