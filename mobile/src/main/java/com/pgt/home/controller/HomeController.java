package com.pgt.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import com.pgt.constant.Constants;
import com.pgt.hot.bean.HotSearch;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.search.bean.ESSort;
import com.pgt.search.service.ESSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-1-14.
 */
@Controller
public class HomeController{
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private ESSearchService   esSearchService;
    @Autowired
    private BannerService     bannerService;

    @RequestMapping("/")
    public ModelAndView home(ModelAndView modelAndView){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        ESSort esSort = new ESSort();
        esSort.setPropertyName(Constants.SORT);
        esSort.setSortOrder(SortOrder.ASC);
        SearchResponse searchResponse = esSearchService.findHotSales(esSort);
        if (!ObjectUtils.isEmpty(searchResponse)) {
            List hotProducts= searchConvertToList(searchResponse);
            responseMap.put("hotProducts",hotProducts);
        }
        // get hot search
        List<HotSearch> hotSearchList = productServiceImp.queryAllHotsearch();
        responseMap.put("hotSearchList",hotSearchList);

        Banner banner = bannerService.queryBannerByType(Constants.BANNER_TYPE_HOME);
        responseMap.put("banner",banner);
        Map<String,Object> dataMap = responseMap;
        modelAndView.setViewName("home/home");
        LOGGER.debug("******************************1*" + dataMap.get("hotProducts"));
        LOGGER.debug("******************************" + JSONObject.toJSONString(dataMap));
        modelAndView.addObject("data", dataMap);
        return modelAndView;
    }

    protected List  searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List message = new ArrayList();
        for(SearchHit searchit:searchHits){
            message.add(searchit.getSource());
        }
        return message;
    }
}
