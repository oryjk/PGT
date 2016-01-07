package com.pgt.permission;

import com.pgt.constant.PathConstant;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 10/26/15.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        log.info("requestUri:" + requestUri);
        log.info("contextPath:" + contextPath);
        log.info("url:" + url);
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            log.debug("Interceptor：go to login page！");
            if (url.matches(PathConstant.NO_LOGIN_INTERCEPTOR_PATH)) {
                log.debug("Not need interceptor.");
                return true;
            }
            response.sendRedirect(PathConstant.LOGIN_PATH);
            return false;
//            request.getRequestDispatcher(PathConstant.LOGIN_PATH).forward(request, response);
//            return false;
        }

        log.debug("logged user");
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
