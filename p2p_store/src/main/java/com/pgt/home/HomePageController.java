package com.pgt.home;

import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.common.service.BannerService;
import com.pgt.constant.Constants;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.tender.service.TenderService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by carlwang on 1/26/16.
 */

@RestController
public class HomePageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("/index/index");
        buildBanner(modelAndView);
        buildHotSearch(modelAndView);
        buildCategoryHotTender(modelAndView);
        buildSiteHotTender(modelAndView);
        buildCategoryTender(modelAndView);
        return modelAndView;
    }

    private void buildHotSearch(ModelAndView modelAndView) {

    }

    private void buildBanner(ModelAndView modelAndView) {
        Banner banner = bannerService.queryBannerByTypeAndWebSite(Constants.BANNER_TYPE_HOME, BannerWebSite.P2P_STORE.toString());
        if (!ObjectUtils.isEmpty(banner)) {
            LOGGER.debug("The query banner id is {}", banner.getBannerId());
            modelAndView.addObject("banner", banner);
        }
    }

    private void buildCategoryTender(ModelAndView modelAndView) {

    }

    private void  buildCategoryHotTender(ModelAndView modelAndView) {
        SearchResponse categoryHotResponse = tenderSearchEngineService.findCategoryHotTender();

        if (!ObjectUtils.isEmpty(categoryHotResponse)) {
            buildTender(categoryHotResponse, modelAndView, Constants.CATEGORY_HOT);
        }
        LOGGER.debug("The site hot tender is  empty.");

    }

    private void buildSiteHotTender(ModelAndView modelAndView) {
        SearchResponse siteHotResponse = tenderSearchEngineService.findSiteHotTender();
        if(siteHotResponse.getHits().getHits().length<3){
            siteHotResponse=tenderSearchEngineService.findTender(null,null,null,null,null,null,null);
        }
        if (!ObjectUtils.isEmpty(siteHotResponse)) {
            buildTender(siteHotResponse, modelAndView, Constants.SITE_HOT);
        }
        LOGGER.debug("The category hot tender is  empty.");

    }

    private void buildTender(SearchResponse siteHotResponse, ModelAndView modelAndView, String attributeName) {
        SearchHits searchHits = siteHotResponse.getHits();
        if (ObjectUtils.isEmpty(searchHits)) {
            LOGGER.debug("The {} is empty.", attributeName);
            return;
        }
        SearchHit searchHit[] = searchHits.getHits();
        if (searchHit.length > 0) {
            modelAndView.addObject(attributeName, searchHit);
            return;
        }
        LOGGER.debug("The {} is empty.", attributeName);
    }


}
