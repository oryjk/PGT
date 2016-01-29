package com.pgt.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 12/10/15.
 */
public class MPDispatcherServlet extends DispatcherServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MPDispatcherServlet.class);

    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.warn("The request servlet is {}.", request.getServletPath());
        LOGGER.warn("The request url is {}.", request.getRequestURI());
        response.sendRedirect(request.getContextPath() + "/notFound");
    }
}
