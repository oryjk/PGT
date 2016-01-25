package com.pgt.web.search.Controller;

import com.alibaba.fastjson.JSONObject;
import com.pgt.search.controller.EssearchBean;
import com.pgt.search.service.ESSearchService;
import com.pgt.web.search.AbstractController.SearchBaseController;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-1-16.
 */
@Controller
@RequestMapping("web")
public class SearchController extends SearchBaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ESSearchService esSearchService;

    @RequestMapping("search")
    public ModelAndView search(ModelAndView modelAndView){
        List categorys = this.findCategories();
        modelAndView.addObject("categorys", categorys);
        modelAndView.setViewName("search/search");
        return  modelAndView;
    }

    @RequestMapping("searchProduct")
    public ModelAndView SearchProduct(EssearchBean essearchBean,ModelAndView modelAndView){
        essearchBean.setMobileCapacity("5");
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("products", this.essearchService(essearchBean).get("products"));
        modelAndView.addObject("bean", map);
        LOGGER.debug("***" + this.essearchService(essearchBean).get("products"));
        LOGGER.debug("***" + JSONObject.toJSONString(this.essearchService(essearchBean).get("commPaginationBean")));
        modelAndView.addObject("essearchBean",essearchBean);
        modelAndView.addObject("page", this.essearchService(essearchBean).get("commPaginationBean"));
        modelAndView.setViewName("search/mycollection");
        return modelAndView;
    }

    @RequestMapping("searchProductDetails")
    public ModelAndView searchProductDetails(@RequestParam String id, ModelAndView modelAndView){
        List productIDList = new ArrayList<>();
        productIDList.add(id);
        SearchResponse r = esSearchService.findProductsByProductIds(productIDList);
        SearchHits hits = r.getHits();
        SearchHit[] productsArr = hits.getHits();
        LOGGER.debug("***" + JSONObject.toJSONString( productsArr[0].getSource()));
        modelAndView.addObject("product", productsArr[0].getSource());
        modelAndView.setViewName("search/product-details");
        return modelAndView;
    }
}
