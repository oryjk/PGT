package com.pgt.help.interceptor;

import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xiaodong on 15-12-21.
 */
public class HelpCenterInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCenterInterceptor.class);
    @Autowired
    private HelpCenterService helpCenterService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HelpCenter helpCenter = new HelpCenter();
        helpCenter.setSite(HelpCenterSites.B2C_STORE.toString());
        LOGGER.debug("The query helpCenter site is {}",HelpCenterSites.B2C_STORE.toString());
        List<HelpCategoryVo> helpCategorVoList = helpCenterService.findAllHelpByQuery(helpCenter);
        if(!ObjectUtils.isEmpty(helpCategorVoList)){
            request.setAttribute("helpCenterList",helpCategorVoList);
            LOGGER.debug("The query helpCenterList size is {}",helpCategorVoList.size());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
