package com.pgt.constant;

/**
 * Created by carlwang on 10/26/15.
 */
public class PathConstant {

    public static String LOGIN_PATH = "/WEB-INF/views/jsp/user/login.jsp";
    public static String LOGIN_PAGE = "/user/login";
    public static String RESET_PASSWORD = ".*/((resetPassword))";
    public static String NO_LOGIN_INTERCEPTOR_PATH = ".*/((login))|.*/((logout))|.*/((updatePassword))|.*/((updatePasswordSubmit))|.*/((register))|.*/((mLogin))|.*/((mRegister))|.*/((resetPassword))|.*/((yeepayAccountInfo))|.*/web/.*|.*resources/.*";
    public static String NO_LOGIN_TOKEN_PATH = ".*/((login))|.*/((logout))|.*/((updatePassword))|.*/((updatePasswordSubmit))|.*/((register))|.*/((mLogin))|.*/((mRegister))|.*/((resetPassword))|.*/((yeepayAccountInfo))|.((mHome))|.((mEssearch))/.*|.((mProduct))/.*|.((mConsulting))/((query))|.((mDiscuss))/((query))";
}
