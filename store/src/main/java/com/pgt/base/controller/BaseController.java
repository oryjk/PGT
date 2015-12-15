package com.pgt.base.controller;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cwang7 on 10/29/15.
 */
public interface BaseController<T> {

    ModelAndView create(T t, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response);

    ModelAndView delete(String objectId, ModelAndView modelAndView, HttpServletRequest request,
            HttpServletResponse response);
}
