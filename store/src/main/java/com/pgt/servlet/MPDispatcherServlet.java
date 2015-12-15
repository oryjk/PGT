package com.pgt.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 12/10/15.
 */
public class MPDispatcherServlet extends DispatcherServlet {

    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.sendRedirect(request.getContextPath() + "/notFound");
    }
}
