package com.pgt.user.Interceptor;

import com.pgt.configuration.Configuration;
import com.pgt.constant.UserConstant;
import com.pgt.sso.service.SSOService;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaodong on 16-2-25.
 */
public class SSOInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSOInterceptor.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("The method is SSO Interceptor");

        User user= (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        LOGGER.debug("The session createTime {}",request.getSession().getCreationTime());
        LOGGER.debug("The session lastAccessTime {}",request.getSession().getLastAccessedTime());
        if(!ObjectUtils.isEmpty(user)){
            LOGGER.debug("The user is login,not need Interceptor");
            return true;
        }

        Cookie [] cookies= request.getCookies();
        String token=null;
        if(ObjectUtils.isEmpty(cookies)){
            LOGGER.debug("The cookie is empty");
           return true;
        }
        for (Cookie cookie:cookies) {
             LOGGER.debug("cookie name is {}.,values is {}.",cookie.getName(),cookie.getValue());
             if (cookie.getName().endsWith(configuration.getUserCacheTokenKey())){
                   token=cookie.getValue();
                   break;
             }
        }
        if(StringUtils.isEmpty(token)){
            LOGGER.debug("The cookie values is empty,create login session is fail");
            return true;
        }

       if(ssoService.isUserExpire(token)){
           LOGGER.debug("The userCacheToken is not expire");
           return true;
        }

        LOGGER.debug("The userCacheToken is expire");
        String username=ssoService.findUserUsername(token);
        if(ObjectUtils.isEmpty(username)){
           LOGGER.debug("The username is not find");
            return true;
        }
        LOGGER.debug("The user username is {}.",username);
        User newUser=userService.authorize(username);

        if(ObjectUtils.isEmpty(newUser)){
            LOGGER.debug("The findUser by Id is empty username is {}.",username);
            return true;
        }
        request.getSession().setAttribute(UserConstant.CURRENT_USER,newUser);
        LOGGER.debug("The SSOInterceptor create user is success, and user id is {}.",newUser.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

}
