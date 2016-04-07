package com.pgt.help.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.StaticResourceSearchEngineService;
import com.pgt.utils.SearchConvertToList;
import org.apache.commons.collections.MapUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Map<Object,Object>> helpList = SearchConvertToList.requestAttributeConvertToList(request,"helpCategoryVoList");
        //List helpFilterList = new ArrayList<>();
        for(int i = 0;i < helpList.size();i++){
            List<Map<String,Object>> list = (ArrayList)helpList.get(i).get("helpCenterList");
            for(int j = 0; j< list.size(); j++){
                if(!ObjectUtils.isEmpty(list.get(j).get("site")) && list.get(j).get("site").toString().contentEquals(HelpCenterSites.B2C_STORE.toString())){
                    //helpFilterList.add(list.get(j));
                    list.set(j, null);
                }
            }
        }
        LOGGER.debug("" + helpList);
        //helpList = helpList.stream().filter(entry -> entry.get(Constants.P2P_HELPCENTER) != HelpCenterSites.P2P_STORE).collect(Collectors.toList());
        request.setAttribute("helpCategorViewList", helpList);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
