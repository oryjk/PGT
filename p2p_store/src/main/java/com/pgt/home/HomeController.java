package com.pgt.home;

import com.pgt.category.bean.CategoryType;
import com.pgt.common.bean.Banner;
import com.pgt.constant.Constants;
import com.pgt.home.controller.BaseHomeController;
import com.pgt.search.service.CategorySearchEngineService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.utils.SearchConvertToList;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
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
        buildSiteOnSaleTender(modelAndView);
        buildRootCategory(modelAndView);
        buildCategoryOnSaleTender(modelAndView);
        return modelAndView;
    }

    private void buildBanner(ModelAndView modelAndView) {
        Banner banner = queryBanner(Constants.BANNER_TYPE_HOME);
        modelAndView.addObject(Constants.BANNER, banner);
    }

    private void buildRootCategory(ModelAndView modelAndView) {
        SearchResponse response = categorySearchEngineService.findRootCategory(CategoryType.TENDER_ROOT);
        List<Map<String, Object>> rootCategories = SearchConvertToList.searchConvertToList(response);
        modelAndView.addObject(Constants.ROOT_CATEGORIES, rootCategories);
    }

    private void buildCategoryOnSaleTender(ModelAndView modelAndView) {
        //TODO
        List<Map<String, Object>> rootCategories = (List<Map<String, Object>>) modelAndView.getModel().get(Constants.ROOT_CATEGORIES);
        if (ObjectUtils.isEmpty(rootCategories)) {
            LOGGER.debug("Can not query hot category tender,because the root category is empty.");
            return;
        }
        rootCategories.stream().filter(stringObjectMap -> CollectionUtils.isEmpty((List) stringObjectMap.get(Constants.CHILDREN)));

        SearchResponse categoryHotResponse = tenderSearchEngineService.findCategoryHotTender();

        if (!ObjectUtils.isEmpty(categoryHotResponse)) {
            List<Map<String, Object>> categoryOnSale = SearchConvertToList.searchConvertToList(categoryHotResponse);
            modelAndView.addObject(Constants.CATEGORY_HOT, categoryOnSale);
            return;
        }
        LOGGER.debug("The site hot tender is  empty.");

    }

    private void buildSiteOnSaleTender(ModelAndView modelAndView) {
        SearchResponse siteHotResponse = tenderSearchEngineService.findSiteHotTender();
        if (siteHotResponse.getHits().getHits().length < 3) {
            siteHotResponse = tenderSearchEngineService.findTender(null, null, null, null, null, null, null);
        }
        if (!ObjectUtils.isEmpty(siteHotResponse)) {
            List<Map<String, Object>> siteOnSale = SearchConvertToList.searchConvertToList(siteHotResponse);
            if (siteOnSale.size() > 3) {
                siteOnSale.subList(0, 2);
            }
            modelAndView.addObject(Constants.SITE_HOT, siteOnSale);
            return;
        }
        LOGGER.debug("The category hot tender is  empty.");

    }


}
