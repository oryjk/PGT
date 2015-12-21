package com.pgt.help.Interceptor;

import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.service.HelpCenterService;
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


    @Autowired
    private HelpCenterService helpCenterService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
        if(!ObjectUtils.isEmpty(HelpCategorVoList)){
            request.setAttribute("helpCenterList",HelpCategorVoList);
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
