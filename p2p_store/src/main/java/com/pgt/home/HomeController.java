package com.pgt.home;

import com.pgt.category.bean.CategoryType;
import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import com.pgt.constant.Constants;
import com.pgt.home.controller.BaseHomeController;
import com.pgt.search.service.CategorySearchEngineService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.utils.SearchConvertToList;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 1/26/16.
 */

@RestController
public class HomeController extends BaseHomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;

    @Autowired
    private CategorySearchEngineService categorySearchEngineService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("/index/index");
        buildBanner(modelAndView);
        buildHotProduct(modelAndView);
        buildCategoryTender(modelAndView);
        buildCategoryHotTender(modelAndView);
        buildSiteHotTender(modelAndView);
        return modelAndView;
    }

    private void buildHotProduct(ModelAndView modelAndView) {
        SearchResponse response = tenderSearchEngineService.findSiteHotTender();
        List<Map<String, Object>> hotProducts = SearchConvertToList.searchConvertToList(response);
        modelAndView.addObject("hotProducts", hotProducts);
    }

    private void buildBanner(ModelAndView modelAndView) {
        Banner banner = queryBanner(Constants.BANNER_TYPE_HOME);
        modelAndView.addObject("banner", banner);
    }

    private void buildCategoryTender(ModelAndView modelAndView) {
        SearchResponse response = categorySearchEngineService.findRootCategory(CategoryType.TENDER_ROOT);
        List<Map<String, Object>> rootCategories = SearchConvertToList.searchConvertToList(response);
        modelAndView.addObject(Constants.ROOT_CATEGORIES, rootCategories);
    }

    private void buildCategoryHotTender(ModelAndView modelAndView) {
        //TODO
        List<Map<String, Object>> rootCategories = (List<Map<String, Object>>) modelAndView.getModel().get(Constants.ROOT_CATEGORIES);
        if (ObjectUtils.isEmpty(rootCategories)) {
            LOGGER.debug("Can not query hot category tender,because the root category is empty.");
            return;
        }
        rootCategories.stream().filter(stringObjectMap -> CollectionUtils.isEmpty((List) stringObjectMap.get(Constants.CHILDREN)));

        SearchResponse categoryHotResponse = tenderSearchEngineService.findCategoryHotTender();

        if (!ObjectUtils.isEmpty(categoryHotResponse)) {
            buildTender(categoryHotResponse, modelAndView, Constants.CATEGORY_HOT);
        }
        LOGGER.debug("The site hot tender is  empty.");

    }

    private void buildSiteHotTender(ModelAndView modelAndView) {
        SearchResponse siteHotResponse = tenderSearchEngineService.findSiteHotTender();
        if (siteHotResponse.getHits().getHits().length < 3) {
            siteHotResponse = tenderSearchEngineService.findTender(null, null, null, null, null, null, null);
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
