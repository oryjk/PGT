package com.pgt.home.controller;

import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.common.service.BannerService;
import com.pgt.constant.Constants;
import com.pgt.hot.bean.HotSearch;
import com.pgt.base.controller.BaseMobileController;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.bean.HotProducts;
import com.pgt.search.controller.ESSearchConstants;
import com.pgt.search.service.ESSearchService;
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

import java.util.ArrayList;
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
                List hotProducts= findHotSale();
                LOGGER.debug("add hotProducts to json");
                responseMap.put("hotProducts",hotProducts);
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

    public  List<HotProducts> findHotSale(){

        List<HotProducts> products= new ArrayList<>();
        LOGGER.debug("The  method find hotSaleProduct");
        SearchResponse searchResponse=esSearchService.findRootCategory();
        SearchHits categoryHits=searchResponse.getHits();
        SearchHit[] categoryHist=categoryHits.getHits();
        if(ArrayUtils.isEmpty(categoryHist)){
            LOGGER.debug("The findHotSaleProduct is empty");
            return null;
        }
        List<ESTerm> listTerm = new ArrayList<>();
        ESTerm esMatch = new ESTerm();
        esMatch.setPropertyName(ESSearchConstants.HOT_PROPERTY);
        LOGGER.debug("The HOT_PROPERTY is {}",ESSearchConstants.HOT_PROPERTY);
        esMatch.setTermValue(ESSearchConstants.HOT_VALUE);
        LOGGER.debug("The HOT_VALUE is {}",ESSearchConstants.HOT_VALUE);
        listTerm.add(esMatch);

        CommPaginationBean paginationBean = new CommPaginationBean();
        paginationBean.setCurrentIndex(0);
        paginationBean.setCapacity(ESSearchConstants.HOT_SIEZ);
        LOGGER.debug("The HOT_SIZE is {}",ESSearchConstants.HOT_SIEZ);

        ESSort esSort = new ESSort();
        esSort.setPropertyName(ESSearchConstants.SORT_PROPERTY);
        esSort.setSortOrder(SortOrder.ASC);
        List<ESSort> sortList= new ArrayList<>();
        sortList.add(esSort);

        for (SearchHit category:categoryHist) {
            HotProducts  hot = new HotProducts();
            Integer categoryId = (Integer) category.getSource().get("id");
            LOGGER.debug("The  category Id is {}",categoryId);

            //  SearchResponse categoryProduct=esSearchService.findProductsByCategoryId(categoryId.toString(),listTerm ,null,paginationBean,null,sortList);
            ESTerm esterm = new ESTerm();
            esterm.setPropertyName("rootCategoryId");
            esterm.setTermValue(categoryId.toString());
            listTerm.add(esterm);
            SearchResponse categoryProduct =esSearchService.findProducts(null,listTerm,null,sortList,paginationBean,null,true);

            if(!ObjectUtils.isEmpty(categoryProduct)){
                SearchHits categoryProducts=categoryProduct.getHits();
                SearchHit[] hotProducts =categoryProducts.getHits();
                if(hotProducts.length<6){
                    LOGGER.debug("The  hotProducts is less than 6,so find to all products");
                    // SearchResponse otherCategoryProduct=esSearchService.findProductsByCategoryId(categoryId.toString(),null ,null,paginationBean,null,sortList);
                    listTerm.clear();
                    listTerm.add(esterm);
                    SearchResponse otherCategoryProduct =esSearchService.findProducts(null,listTerm,null,sortList,paginationBean,null,true);
                    SearchHits otherCategoryProducts=otherCategoryProduct.getHits();
                    hotProducts =otherCategoryProducts.getHits();
                }
                hot.setCategory(category);
                hot.setHotProducts(hotProducts);
                products.add(hot);
            }
        }
        return products;
    }



}
