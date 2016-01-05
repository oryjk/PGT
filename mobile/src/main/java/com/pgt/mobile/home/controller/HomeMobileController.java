package com.pgt.mobile.home.controller;

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
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.service.ESSearchService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/mHome")
public class HomeMobileController extends BaseMobileController{

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeMobileController.class);
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

    @RequestMapping(method = RequestMethod.POST)
    public  Map<String,Object> index() {

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
            List<HotSearch> hotSearchList = productService.queryAllHotsearch();
            responseMap.put("hotSearchList",hotSearchList);

             Banner banner = bannerService.queryBanner(1);
             responseMap.put("banner",banner);

             return responseMap;
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
