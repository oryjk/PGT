package com.pgt.constant;

/**
 * Created by carlwang on 10/26/15.
 */
public class PathConstant {

    public static String LOGIN_PATH = "/WEB-INF/views/jsp/user/login.jsp";
    public static String RESET_PASSWORD = ".*/((resetPassword))";
    public static String NO_LOGIN_INTERCEPTOR_PATH = ".*/((login))|.*/((logout))|.*/((register))|.*/((mLogin))|.*/((mRegister))|.*/((resetPassword))";
}