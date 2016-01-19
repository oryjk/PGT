package com.pgt.web.search.Controller;

import com.alibaba.fastjson.JSONObject;
import com.pgt.mobile.search.controller.ESSearchMobileController;
import com.pgt.mobile.search.controller.EssearchBean;
import com.pgt.web.search.AbstractController.SearchBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("search")
    public ModelAndView search(ModelAndView modelAndView){
        modelAndView.setViewName("search/search");
        List categorys = this.findCategories();
        modelAndView.addObject("categorys", categorys);
        return  modelAndView;
    }

    @RequestMapping("searchProduct")
    public ModelAndView SearchProduct(EssearchBean essearchBean,ModelAndView modelAndView){
        modelAndView.setViewName("search/mycollection");
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("products", this.essearchService(essearchBean).get("products"));
        modelAndView.addObject("bean", map);
        LOGGER.debug("*************************" + this.essearchService(essearchBean).get("products"));
        return modelAndView;
    }
}
