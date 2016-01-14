package com.pgt.home.controller;

import com.pgt.category.bean.Category;

import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.common.bean.Banner;
import com.pgt.common.bean.Media;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.hot.bean.HotSearch;
import com.pgt.hot.service.HotProductHelper;
import com.pgt.media.MediaService;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.service.ESSearchService;

import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;
import com.pgt.style.service.PageBackgroundService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 10/20/15.
 */
@RestController
@RequestMapping(value = "/")
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private HotProductHelper hotProductHelper;
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
    private MediaService mediaService;

    @Autowired
    private PageBackgroundService pageBackgroundService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("urlConfiguration", urlConfiguration);


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
            Banner banner = bannerService.queryBannerByType(Constants.BANNER_TYPE_HOME);
            if (!ArrayUtils.isEmpty(hotProducts)) {
                modelAndView.addObject("hotProducts", hotProducts);
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
            modelAndView.addObject("hotSearchList", hotSearchList);
            modelAndView.addObject("banner", banner);
            modelAndView.setViewName("/index/index");
        } else {


            // get hot search
            List<HotSearch> hotSearchList = productService.queryAllHotsearch();
            modelAndView.addObject("hotSearchList", hotSearchList);
            Banner banner = bannerService.queryBannerByType(Constants.BANNER_TYPE_HOME);
            modelAndView.addObject("banner", banner);
            modelAndView.setViewName("/index/index");

        }

        modelAndView.addObject("ES", configuration.getUseES());

        return modelAndView;

    }

    public CategoryHelper getCategoryHelper() {
        return categoryHelper;
    }

    public void setCategoryHelper(CategoryHelper categoryHelper) {
        this.categoryHelper = categoryHelper;
    }

    public HotProductHelper getHotProductHelper() {
        return hotProductHelper;
    }

    public void setHotProductHelper(HotProductHelper hotProductHelper) {
        this.hotProductHelper = hotProductHelper;
    }

    public URLConfiguration getUrlConfiguration() {
        return urlConfiguration;
    }

    public void setUrlConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public BannerService getBannerService() {
        return bannerService;
    }

    public void setBannerService(BannerService bannerService) {
        this.bannerService = bannerService;
    }


    public MediaService getMediaService() {
        return mediaService;
    }

    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }
}
