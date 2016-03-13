package com.pgt;

import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 3/12/16.
 */
public class URLInterceptor implements HandlerInterceptor {

    @Autowired
    private URLConfiguration urlConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (ObjectUtils.isEmpty(modelAndView)) {
            //may ajax request.
            return;
        }
        modelAndView.addObject(Constants.B2C_STORE_HOST, urlConfiguration.getB2cStoreUrl());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
