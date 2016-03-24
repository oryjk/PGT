package com.pgt.help.interceptor;

import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.StaticResourceSearchEngineService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiaodong on 15-12-21.
 */
public class HelpCenterInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCenterInterceptor.class);
    @Autowired
    private HelpCenterService helpCenterService;
    @Autowired
    private Configuration configuration;

    @Autowired
    private StaticResourceSearchEngineService staticResourceSearchEngineService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        HelpCenter helpCenter = new HelpCenter();
        helpCenter.setSite(HelpCenterSites.P2P_STORE.toString());
        LOGGER.debug("The query helpCenter site is {}",HelpCenterSites.P2P_STORE);
        List<HelpCategoryVo> helpCategorVoList = helpCenterService.findAllHelpByQuery(helpCenter);
        if(!ObjectUtils.isEmpty(helpCategorVoList)){
            request.setAttribute("helpCenterList",helpCategorVoList);
            LOGGER.debug("The query helpCenterList size is {}",helpCategorVoList.size());
        }
        */

        //查出category
        ESTerm esTerm = new ESTerm();
        esTerm.setTermValue(HelpCenterSites.P2P_STORE.toString());
        esTerm.setPropertyName(Constants.P2P_HELPCENTER);
        List<ESTerm> esTermsList = Arrays.asList(esTerm);
        SearchResponse helpCenterResponse=staticResourceSearchEngineService.findHelpCenter(null,esTermsList,null,null,null,null);
        SearchHit[] helpCategoryVoList=helpCenterResponse.getHits().getHits();
        request.setAttribute("helpCategoryVoList",helpCategoryVoList);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        configuration.getCurrentSite();
        SearchHit[] hits = (SearchHit[])request.getAttribute("helpCategoryVoList");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
