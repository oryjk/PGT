package com.pgt.permission;

import com.pgt.constant.Constants;
import com.pgt.constant.PathConstant;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import com.pgt.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 10/26/15.
 */
public class PersistentInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        if (!url.matches(PathConstant.RESET_PASSWORD)) {
            request.getSession().removeAttribute(Constants.STEP);
        }
        if (request.getSession().getAttribute(UserConstant.CURRENT_USER) != null) {
            return true;
        }

        //find the user in cookie.
        if (!ObjectUtils.isEmpty(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(UserConstant.CURRENT_USER)) {
                    String code = cookie.getValue();
                    String userId = CookieUtils.decodeBase64(code);
                    User user = userService.findUser(Integer.valueOf(userId));
                    if (user != null) {
                        request.getSession().setAttribute(UserConstant.CURRENT_USER, user);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
