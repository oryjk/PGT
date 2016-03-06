package com.pgt.interceptor;

import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.StaticResourceSearchEngineService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaodong on 15-12-21.
 */
public class HelpCenterInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCenterInterceptor.class);
    @Autowired
    private HelpCenterService helpCenterService;
    @Autowired
    private StaticResourceSearchEngineService staticResourceSearchEngineService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        HelpCenter helpCenter = new HelpCenter();
        helpCenter.setSite(HelpCenterSites.B2C_STORE.toString());
        LOGGER.debug("The query helpCenter site is {}",HelpCenterSites.B2C_STORE.toString());
        List<HelpCategoryVo> helpCategoryVoList = helpCenterService.findAllHelpByQuery(helpCenter);
        if(!ObjectUtils.isEmpty(helpCategoryVoList)){
            request.setAttribute("helpCenterList",helpCategoryVoList);
            LOGGER.debug("The query helpCenterList size is {}", helpCategoryVoList.size());
        }
        */
        //查出category
        ESTerm esTerm = new ESTerm();
        esTerm.setTermValue(HelpCenterSites.P2P_STORE.toString());
        esTerm.setPropertyName(Constants.P2P_HELPCENTER);
        SearchResponse helpCenterResponse=staticResourceSearchEngineService.findHelpCenter(null,null,null,null,null,null);
        SearchHit[] helpCategoryVoList=helpCenterResponse.getHits().getHits();
        request.setAttribute("helpCategoryVoList",helpCategoryVoList);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
