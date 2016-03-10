package com.pgt.session;

import com.pgt.cart.bean.Order;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sam Li on 2016/1/17.
 */
public class SessionHelper {

    public static User getUser(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        return (User)pRequest.getSession().getAttribute(UserConstant.CURRENT_USER);
    }

    public static Order getOrder(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        return null;
    }
}
