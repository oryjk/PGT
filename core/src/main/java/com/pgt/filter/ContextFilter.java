package com.pgt.filter;

import com.pgt.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * This class is used to add some context attributes.
 * Created by carlwang on 11/15/15.
 */
public class ContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        if (StringUtils.isBlank((CharSequence) servletContext.getAttribute(Constants.JUEDANGPIN_STATIC_PATH_KEY))) {
            servletContext.setAttribute(Constants.JUEDANGPIN_STATIC_PATH_KEY, Constants.JUEDANGPIN_STATIC_PATH_VALUE);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
