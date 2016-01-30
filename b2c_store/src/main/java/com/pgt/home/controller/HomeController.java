package com.pgt.home.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.common.bean.Media;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.hot.bean.HotSearch;
import com.pgt.media.MediaService;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;
import com.pgt.style.service.PageBackgroundService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 10/20/15.
 */
@RestController
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ProductService productService;

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ESConfiguration esConfiguration;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private PageBackgroundService pageBackgroundService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("urlConfiguration", urlConfiguration);
        LOGGER.debug("HomeController is run");
        List<Category> copyWriter = categoryHelper.findCategoryByType(CategoryType.COPY_WRITER);
        if (!ObjectUtils.isEmpty(copyWriter)) {
            Media copyWriterMedia = mediaService.findCopyWriterMedia(copyWriter.get(0).getId());
            modelAndView.addObject("copyWriter", copyWriterMedia);
        }
        if (configuration.getUseES() == true) {
            ESSort esSort = new ESSort();
            esSort.setPropertyName(Constants.SORT);
            esSort.setSortOrder(SortOrder.ASC);
            SearchResponse searchResponse = esSearchService.findHotSales(esSort);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] hotProducts = searchHits.getHits();
            if (!ArrayUtils.isEmpty(hotProducts)) {
                modelAndView.addObject("hotProducts", hotProducts);
                LOGGER.debug("add hotProducts to modelAndView");
            }
            //get Pagebackground
            PageBackgroundQuery pageBackgroundQuery = new PageBackgroundQuery();
            pageBackgroundQuery.setNowDate(new Date());
            List<PageBackground> pageBackgroundList=pageBackgroundService.queryPageBackground(pageBackgroundQuery);
            if(!CollectionUtils.isEmpty(pageBackgroundList)){
                modelAndView.addObject("pageBackground",pageBackgroundList.get(0));
            }
            // get hot search
            List<HotSearch> hotSearchList = productService.queryAllHotsearch();
            LOGGER.debug("add hotSearchList to modelAndView");
            modelAndView.addObject("hotSearchList", hotSearchList);

            Banner banner=  bannerService.queryBannerByTypeAndWebSite(Constants.BANNER_TYPE_HOME,BannerWebSite.B2C_STORE.toString());
            if(!ObjectUtils.isEmpty(banner)){
                LOGGER.debug("The query banner id is {}",banner.getBannerId());
                modelAndView.addObject("banner", banner);
            }
            SearchHit[] newProducts=getNewProduct();
            if(!ArrayUtils.isEmpty(newProducts)){
                modelAndView.addObject("newProducts",newProducts);
                LOGGER.debug("add newProducts to modelAndView");
            }
            modelAndView.setViewName("/index/index");
        } else {

            // get hot search
            List<HotSearch> hotSearchList = productService.queryAllHotsearch();
            modelAndView.addObject("hotSearchList", hotSearchList);
            Banner banner=  bannerService.queryBannerByTypeAndWebSite(Constants.BANNER_TYPE_HOME,BannerWebSite.B2C_STORE.toString());
            modelAndView.addObject("banner", banner);
            modelAndView.setViewName("/index/index");

        }

        modelAndView.addObject("ES", configuration.getUseES());

        return modelAndView;

    }

    @RequestMapping(value = "/homeSearch", method = RequestMethod.GET)
    public ModelAndView homeSearch (@RequestParam(value = "term", required = false) String term, ModelAndView modelAndView) {

        LOGGER.debug("The method is to homeSearch");
        List<ESTerm> esMatches = new ArrayList<>();
        LOGGER.debug("add term to ESMatch");
        buildESMatch(term, esMatches);
        SearchResponse searchResponse = esSearchService.findProducts(null, esMatches, null, null, null,
                null, null);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] productList = hits.getHits();
        if (!ArrayUtils.isEmpty(productList)) {
            LOGGER.debug("add productList to model total is {}", productList.length);
            modelAndView.addObject("productList", productList);
        }
        modelAndView.setViewName("/index/classify");
        return modelAndView;
    }

    private void buildESMatch (@RequestParam(value = "term", required = false) String term,
                               List<ESTerm> esMatches) {
        if (!StringUtils.isEmpty(term)) {
            term = term.trim();
            List<String> useToSearch = esConfiguration.getUseToSearch();
            if (!ObjectUtils.isEmpty(useToSearch)) {
                final String finalTerm = term;
                useToSearch.stream().forEach(s -> {
                    ESTerm esMatch = new ESTerm();
                    LOGGER.debug("The propertyName is {}", s);
                    esMatch.setPropertyName(s);
                    esMatch.setTermValue(finalTerm);
                    esMatches.add(esMatch);
                });
            }
            LOGGER.debug("the term is {}", term);
        }
    }


    public SearchHit[] getNewProduct(){
        List<ESSort> sortList=new ArrayList<>();
        ESSort eSSort = new ESSort();
        eSSort.setPropertyName("creationDate");
        eSSort.setSortOrder(SortOrder.DESC);
        sortList.add(eSSort);
        CommPaginationBean paginationBean = new CommPaginationBean();
        paginationBean.setCurrentIndex(0);
        paginationBean.setCapacity(3);
        SearchResponse searchProduct = esSearchService.findProducts(null, null, null,sortList, paginationBean, null, null);
        SearchHits searchHits = searchProduct.getHits();
        SearchHit[] newProducts = searchHits.getHits();
        return newProducts;
    }


}
