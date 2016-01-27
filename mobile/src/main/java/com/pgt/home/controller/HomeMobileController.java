package com.pgt.home.controller;

import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.common.service.BannerService;
import com.pgt.constant.Constants;
import com.pgt.hot.bean.HotSearch;
import com.pgt.base.controller.BaseMobileController;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.service.ESSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping(value = "/mHome")
public class HomeMobileController extends BaseMobileController{

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeMobileController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ESSearchService esSearchService;
    @Autowired
    private BannerService bannerService;


    /**
     * The method query home
     * @return json for hotProducts,hotSearchList,banner
     */
    @RequestMapping(method = RequestMethod.POST)
    public  Map<String,Object> index() {
        LOGGER.debug("The method query home");
        Map<String,Object> responseMap = new HashMap<>();

            ESSort esSort = new ESSort();
            esSort.setPropertyName(Constants.SORT);
            esSort.setSortOrder(SortOrder.ASC);
            SearchResponse searchResponse = esSearchService.findHotSales(esSort);
            if (!ObjectUtils.isEmpty(searchResponse)) {
                List hotProducts= searchConvertToList(searchResponse);
                LOGGER.debug("add hotProducts to json");
                responseMap.put("hotProducts",hotProducts);
            }
            // get hot search
            List<HotSearch> hotSearchList = productService.queryAllHotsearch();
            if(!CollectionUtils.isEmpty(hotSearchList)){
                LOGGER.debug("add hotSearchList to json");
               responseMap.put("hotSearchList",hotSearchList);
             }
             Banner banner=  bannerService.queryBannerByTypeAndWebSite(Constants.BANNER_TYPE_HOME, BannerWebSite.B2C_STORE.toString());
             if(!ObjectUtils.isEmpty(banner)){
                 LOGGER.debug("add banner to json for id {}",banner.getBannerId());
                 responseMap.put("banner",banner);
             }
             return responseMap;
    }


}
